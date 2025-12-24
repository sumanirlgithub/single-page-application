package com.neo.api.order.service;

import com.neo.api.common.enums.OrderStatus;
import com.neo.api.order.client.InventoryFeignClient;
import com.neo.api.order.client.InventoryFeignClientAsyncCall;
import com.neo.api.order.entity.Customer;
import com.neo.api.order.entity.OrderItem;
import com.neo.api.order.entity.OutboundEvent;
import com.neo.api.order.entity.PurchaseOrder;
import com.neo.api.order.exception.OrderServiceException;
import com.neo.api.order.model.CustomerRequestPayload;
import com.neo.api.order.model.Order;
import com.neo.api.order.model.OrderRequestPayload;
import com.neo.api.order.model.OrderResponsePayload;
import com.neo.api.order.publisher.kafka.KafkaPublisher;
import com.neo.api.order.repository.CustomerRepository;
import com.neo.api.order.repository.OrderJpaRepository;
import com.neo.api.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.neo.api.order.repository.OutboundEventJpaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final OutboundEventJpaRepository outboundEventJpaRepository;
    private final JsonUtil jsonUtil;

    private final InventoryFeignClient inventoryFeignClient;
    @LoadBalanced
    private final InventoryFeignClientAsyncCall inventoryFeignClientAsyncCall;
    private final KafkaPublisher kafkaPublisher;

    @Value("${payment.queue}")
    private String paymentQueue;

    /**
     * Get the list of Order Items for the Order - Order can have more than one item.
     *
     */
    public List<String> getOrderItemsForOrder(String orderId) {
        log.info("Retrieve Order items for Order Id: {}", orderId);
        List<String> items = orderRepository.getOrderItemsForOrder(orderId);
        log.info("Number of Items Found: {}", items.size());
        return items;
    }

    public OrderResponsePayload getOrderById(String id) {
        OrderResponsePayload responsePayload = new OrderResponsePayload();
        log.info("Order-ID: " + id);
        //return orderJpaRepository.findByOrderId(id).orElse(null);
        List<OrderItem> orderItems = orderJpaRepository.findByOrderIdWithJoinFetch(id);
        responsePayload.setOrderId(orderItems.stream().findFirst().get().getPurchaseOrder().getOrderId());
        responsePayload.setCustomerId(orderItems.stream().findFirst().get().getPurchaseOrder().getCustomerId());
        responsePayload.setOrderItems(orderItems);
        orderItems.get(0).getPurchaseOrder().getOrderId();
        return responsePayload;
    }

    /**
     * Save order for a given Customer.
     *
     */
    @Transactional(transactionManager = "jpaTransactionManager")
    public Order placeOrder(OrderRequestPayload orderRequest) throws JsonProcessingException, InterruptedException, ExecutionException {
        //Step-1
        Customer customer = customerRepository.findById(orderRequest.getCustomerId())
                .orElseThrow(() -> new OrderServiceException("Customer not found"));

        Map<String, String> headers = new HashMap<>();
        //Step-2 - Asynchronous call to REST api (inventory-service) for checking inventory using CompletableFuture.supplyAsync - non-blocking
        CompletionStage<Boolean> completionStage = callExternalApi(orderRequest, headers);
        boolean isProductInStock = completionStage.toCompletableFuture().get();
        log.info("Received response from inventory-service isProductInStock: " + isProductInStock);
        if (!isProductInStock) {
            throw new RuntimeException("Unable to check the product availability in stock");
        }
        //Step-3 Create an order into database with status 'PENDING'
        PurchaseOrder order = convertOrderRequestToEntity(orderRequest);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCustomerId(customer.getCustomerId());
        orderJpaRepository.save(order);
        log.info("Order successfully created in database with PENDING status");

        //Step-4 Create and Save order event into Outbound_Event table
        OutboundEvent outboundEvent = new OutboundEvent();
        outboundEvent.setOrderId(order.getOrderId());
        outboundEvent.setEventType("ORDER_CREATED");
        outboundEvent.setPayload(jsonUtil.toJson(order));
        outboundEvent.setSent(false);
        outboundEventJpaRepository.save(outboundEvent);
        log.info("Order outbound event successfully saved into database");
        return new Order(order.getOrderId());

        //Step-5 Create a scheduler - Kafka Publisher (Async / Scheduled) for publishing the event stored in database to the KAFKA topic
        // Spring boot scheduler will send order event as java object (JSON) to KAFKA ORDER-TOPIC.
        // This event will be processed by payment-service for further processing.
    }

    @CircuitBreaker(name = "inventoryCircuitBreaker", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventoryTimeLimiter", fallbackMethod = "handleTimeout")
    @Retry(name = "inventoryRetry")
    public CompletionStage<Boolean> callExternalApi(OrderRequestPayload orderRequest, Map<String, String> headers) {
        return CompletableFuture.supplyAsync(() -> {
            // call the external API for checking an item in stock
            return inventoryFeignClientAsyncCall.isInStock(
                    orderRequest.getItems().get(0).getItemCode(), orderRequest.getItems().get(0).getQuantity(), headers);
        });
    }

    /**
     * Fallback method for the circuit breaker
     */
    public CompletionStage<Boolean> fallbackMethod(Exception e) {
        // Fallback Response
        log.error("Unable to check item availability currently, triggering fallback. {}", e.getMessage());
        return CompletableFuture.completedFuture(false);
    }

    /**
     * Fallback method for the circuit breaker
     */
    public CompletionStage<Boolean> handleTimeout(Exception e) {
        // Fallback Response
        log.warn("Timeout occurred, triggering fallback. {}.", e.getMessage());
        return CompletableFuture.completedFuture(false);
    }

    /**
     * Save Customer.
     *
     */
    public String addCustomer(CustomerRequestPayload customerRequest) {
        Customer customer = convertCustomerPayloadToEntity(customerRequest);
        customerRepository.save(customer);
        log.info("id:" +customer.getCustomerId());
        return customer.getCustomerId();
    }
    private PurchaseOrder convertOrderRequestToEntity(OrderRequestPayload orderRequest) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<OrderItem> orderItems = new ArrayList<>();
        purchaseOrder.setOrderDetails(orderRequest.getOrderDetails());
        purchaseOrder.setTotalValue(orderRequest.getAmount());
        orderRequest.getItems().forEach(orderReqItem -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setItemName(orderReqItem.getItemName());
            orderItem.setItemCode(Integer.valueOf(orderReqItem.getItemCode()));
            orderItem.setQuantity(orderReqItem.getQuantity());
            orderItem.setUnitPrice(BigDecimal.valueOf(orderReqItem.getUnitPrice()));
            //orderItem.setOrderId(purchaseOrder.getOrderId());
            orderItem.setPurchaseOrder(purchaseOrder);
            orderItems.add(orderItem);

        });
        purchaseOrder.setItems(orderItems);
        return purchaseOrder;
    }
    private Customer convertCustomerPayloadToEntity(CustomerRequestPayload customerRequest) {
        Customer customer = new Customer();
        customer.setName(customerRequest.getName());
        customer.setEmail(customerRequest.getEmail());
        return customer;
    }


}
