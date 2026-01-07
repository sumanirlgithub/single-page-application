package com.neo.api.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DataJpaTest
public class OrderRepositoryTest {
    @Mock
    EntityManager entityManagerMock;
    @Mock
    Query queryMock;
    @InjectMocks
    OrderRepository orderRepository;

    //@Test
    public void testGetOrderItemsForOrder() {

        //when(entityManagerMock.createNativeQuery(OrderRepository.GET_ITEMS_FOR_ORDER)).thenReturn(queryMock);

        when(queryMock.setParameter(anyString(), anyString())).thenReturn(queryMock);

        when(queryMock.unwrap(Query.class)).thenReturn(queryMock);

        when(queryMock.getResultList()).thenReturn(Arrays.asList("Computer"));

        //List<String> actualItems = orderRepository.getOrderItemsForOrder("101");

        //Assertions.assertThat(actualItems.get(0)).isEqualTo("Computer");
    }
}
