package com.neo.api.order.service;

import com.neo.api.common.enums.OrderEventName;
import com.neo.api.common.enums.OrderStatus;
import com.neo.api.common.order.event.OrderEvent;
import com.neo.api.order.auth.AuthService;
import com.neo.api.order.client.InventoryFeignClient;
import com.neo.api.order.client.InventoryFeignClientAsyncCall;
import com.neo.api.order.client.PaymentFeignClient;
import com.neo.api.order.entity.Customer;
import com.neo.api.order.entity.OrderItem;
import com.neo.api.order.entity.PurchaseOrder;
import com.neo.api.order.exception.OrderServiceException;
import com.neo.api.order.exception.PaymentInsightException;
import com.neo.api.order.model.*;
import com.neo.api.order.publisher.kafka.KafkaPublisher;
import com.neo.api.order.repository.CustomerRepository;
import com.neo.api.order.repository.OrderJpaRepository;
import com.neo.api.order.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.neo.api.order.exception.PaymentInsightExceptionMessage.API_CALL_EXCEPTION;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceRestConsumer {

    private final JmsTemplate jmsTemplate;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderJpaRepository orderJpaRepository;

    private final InventoryFeignClient inventoryFeignClient;
    @LoadBalanced
    private final InventoryFeignClientAsyncCall inventoryFeignClientAsyncCall;
    private final KafkaPublisher kafkaPublisher;

    @Value("${payment.queue}")
    private String paymentQueue;
    @Value("${keycloak.client-id:''}")
    private String clientId;
    @Value("${keycloak.client-secret:''}")
    private String secret;
    private final DiscoveryClient discoveryClient;
    private final PaymentFeignClient paymentFeignClient;
    private final WebClient webClient;
    private final AuthService authService;

    CircuitBreaker circuitBreaker = CircuitBreaker.ofDefaults("myCircuitBreaker");

    /**
     * Make synchronous call to payment-service - Rest FeignClient
     * Make asynchronous call to payment-service - Rest WebClient
     * Implement Circuit breaker design pattern to achieve fault tolerance
     *
     * @param paymentNumber
     * @return
     */
    public ResponseEntity<List<PaymentInsightResponse>> manualRefreshPaymentStatus(String paymentNumber) throws Exception {

        List<String> uetrNumber = Collections.singletonList("6f7a55fb-6ef1-4a26-ba66-a812554937d8");
        PaymentInsightRequest request = new PaymentInsightRequest();
        request.setUetrNumbers(uetrNumber);

        // Synchronous API call to payment-service for retrieving current payment status
        // Calling external endpoint - via FeignClient - tested working!
//        Supplier<ResponseEntity<List<PaymentInsightResponse>>> decoratedSupplier =
//                CircuitBreaker.decorateSupplier(circuitBreaker, () -> callExternalServiceViaFeignClient(request));
//        List<PaymentInsightResponse> failureResponse =
//                Arrays.asList(PaymentInsightResponse.builder().errorMessage("Service is temporarily unavailable. Please try again late").build());
//        return Try.ofSupplier(decoratedSupplier)
//                .recover(throwable -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(failureResponse))
//                .get();

        // Calling external endpoint - via WebClient - tested working!
        return circuitBreaker.executeCallable(() -> callExternalServiceViaWebClient(request));
        //return circuitBreaker.executeRunnable(() -> callExternalServiceViaWebClient(request));
    }

    /**
     * Call Payment Insight endpoint for single request via WebClient (Reactive Way)
     * @param request paymentInsightRequest object with uetrList
     */
    public ResponseEntity<List<PaymentInsightResponse>> callExternalServiceViaWebClient(
            PaymentInsightRequest request) {

        // get physical location from service discovery
        List<ServiceInstance> services = discoveryClient.getInstances("payment-service");
        ServiceInstance service = services.get(0);
        log.info("Uri", "{}", service.getUri());
        log.info("host", "{}", service.getHost());
        String url = service.getUri() + "/api/payment-service/payment/status";
        log.info("Calling shared service payment endpoint for fetch status for manual refresh");
        try {
            // Send the token as Authorization header
            String finalAuthToken = authService.getJwtAuthToken("admin", "admin", clientId, secret);
            log.info("finalAuthToken:" + finalAuthToken);

            List<PaymentInsightResponse> result = webClient.post()
                    //.uri(paymentInsightServiceUrl)
                    .uri(url)
                    //.header("X-IBM-CLIENT-ID", clientId)
                    //.header("X-IBM-CLIENT-SECRET", secret)
                    .header("authorization", "Bearer " + finalAuthToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToFlux(PaymentInsightResponse.class)
                    .collectList().block();


//            Flux<PaymentInsightResponse> result = webClient.post()
//                    //.uri(paymentInsightServiceUrl)
//                    .uri(url)
//                    //.header("X-IBM-CLIENT-ID", clientId)
//                    //.header("X-IBM-CLIENT-SECRET", secret)
//                    .header("authorization", "Bearer " + finalAuthToken)
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//                    .body(BodyInserters.fromValue(request))
//                    .retrieve()
//                    .bodyToFlux(PaymentInsightResponse.class);
//            result.subscribe();

            log.info("Continue main thread");
            log.info("Response received from shared service: " + result);
            savePaymentStatusInDb(result, false);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PaymentInsightException(API_CALL_EXCEPTION, e.getMessage());
        }
    }

    private ResponseEntity<List<PaymentInsightResponse>> callExternalServiceViaVirtualThreadsAndCompletableFuture(PaymentInsightRequest request) {
        HttpClient client = HttpClient.newHttpClient();
        List<String> urls = List.of(
                "https://api.example.com/user",
                "https://api.example.com/orders",
                "https://api.example.com/products"
        );

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {

            List<CompletableFuture<String>> futures =
                    urls.stream()
                            .map(url -> CompletableFuture.supplyAsync(() -> {
                                try {
                                    HttpRequest req = HttpRequest.newBuilder()
                                            .uri(URI.create(url))
                                            .GET()
                                            .build();

                                    return client.send(req, HttpResponse.BodyHandlers.ofString())
                                            .body();

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }, executor))
                            .toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                    .thenRun(() -> {
                        futures.forEach(f -> System.out.println(f.join()));
                    })
                    .join();
        }
        return null;
    }

    private ResponseEntity<List<PaymentInsightResponse>> callExternalServiceViaFeignClient(
            PaymentInsightRequest paymentStatusRequest) {

        // Send the token as Authorization header
        String finalAuthToken = "finalAuthToken";
        //String finalAuthToken = authService.getJwtTokenForPaymentService();
        Map<String, String> headers = new HashMap<>();
        headers.put("authorization", "Bearer " + finalAuthToken);
        headers.put("content-type", "application/json");
        headers.put("accept", "application/json");
        ResponseEntity<List<PaymentInsightResponse>> response = null;
        try {
            log.info("Calling external service - payment-service API");
            response =
                    paymentFeignClient.getPaymentStatusFromPaymentApi(paymentStatusRequest, headers);
            if (response.getStatusCode() == HttpStatus.OK) {
                response.getBody().forEach(paymentResponse -> System.out.println(paymentResponse));
            }
        } catch (HttpClientErrorException e) {
            log.error("Unable to receive payment status - HttpStatusCode: {}, Exception Occurred {}", e.getStatusCode(), e.getMessage());
        } catch (Exception e) {
            log.error("Unable to receive payment status Exception Occurred {}", e.getMessage());
        }
        return response;
    }

    private void savePaymentStatusInDb(List<PaymentInsightResponse> paymentInsightResponses, boolean isAsync) {
        log.info("Save status of payment in DB {}", paymentInsightResponses);
    }

}
