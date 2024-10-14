package com.ing.market.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

import com.ing.market.dto.GetOrderResponseDTO;
import com.ing.market.model.Customer;
import com.ing.market.model.Orders;
import com.ing.market.model.enums.Status;
import com.ing.market.repository.OrderRepository;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class OrderServiceListOrderTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private List<Orders> orders;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);
    }

    @Test
    void testListOrdersSuccess() {
        // Arrange
        Instant startDate = Instant.now().minusSeconds(3600);
        Instant endDate = Instant.now(); // Now

        orders = new ArrayList<>();
        Orders order1 = new Orders();
        order1.setId(1L);
        order1.setStatus(Status.PENDING.name());
        order1.setCustomerId(customer.getId());
        order1.setCreateDate(Instant.now().minusSeconds(1800));
        orders.add(order1);

        Orders order2 = new Orders();
        order2.setId(2L);
        order2.setStatus(Status.PENDING.name());
        order2.setCustomerId(customer.getId());
        order2.setCreateDate(Instant.now().minusSeconds(7200));
        orders.add(order2);

        doReturn(orders).when(orderRepository)
                .findByCustomerIdAndStatusAndCreateDateBetween(eq(customer.getId()), eq(Status.PENDING.name()),
                        eq(startDate), eq(endDate), Mockito.any(PageRequest.class));

        // Act
        GetOrderResponseDTO response = orderService.list(startDate, endDate, customer.getId(), PageRequest.of(0, 20));

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getOrderDtos().size());
    }

    @Test
    void testListOrdersNoOrders() {
        // Arrange
        Instant startDate = Instant.now().minusSeconds(3600);
        Instant endDate = Instant.now(); // Now

        doReturn(new ArrayList<>()).when(orderRepository)
                .findByCustomerIdAndStatusAndCreateDateBetween(eq(customer.getId()), eq(Status.PENDING.name()),
                        eq(startDate), eq(endDate), Mockito.any(PageRequest.class));

        // Act
        GetOrderResponseDTO response = orderService.list(startDate, endDate, customer.getId(), PageRequest.of(0, 20));

        // Assert
        assertNotNull(response);
        assertTrue(response.getOrderDtos().isEmpty());
    }
}
