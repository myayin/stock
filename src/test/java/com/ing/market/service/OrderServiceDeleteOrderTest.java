package com.ing.market.service;

import static com.ing.market.constants.ResponseCode.E_ORDER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Customer;
import com.ing.market.model.Orders;
import com.ing.market.model.enums.Status;
import com.ing.market.repository.OrderRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceDeleteOrderTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStrategyContext orderStrategyContext;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private Orders order;
    private Long orderId = 1L;

    @BeforeEach
    void setUp() {
        // Initializing test customer
        customer = new Customer();
        customer.setId(1L);

        // Initializing test order
        order = new Orders();
        order.setId(orderId);
        order.setStatus(Status.PENDING.name());
        order.setCustomerId(customer.getId());
    }

    @Test
    void testDeleteOrderSuccess() throws MarketException {
        // Arrange
        when(orderRepository.findByIdAndStatusAndCustomerId(orderId, Status.PENDING.name(), customer.getId()))
                .thenReturn(Optional.of(order));

        // Act
        orderService.deleteOrder(orderId, customer.getId());

        // Assert
        verify(orderStrategyContext, times(1)).unblockAsset(any(OrderDto.class));
        verify(orderRepository, times(1)).save(order);
        assertEquals(Status.CANCELED.name(), order.getStatus());
    }

    @Test
    void testDeleteOrderNotFound() {
        // Arrange
        when(orderRepository.findByIdAndStatusAndCustomerId(orderId, Status.PENDING.name(), customer.getId()))
                .thenReturn(Optional.empty());

        // Act & Assert
        MarketException exception = assertThrows(MarketException.class, () -> {
            orderService.deleteOrder(orderId, customer.getId());
        });

        assertEquals(E_ORDER_NOT_FOUND.name(), exception.getErrorCode());
        assertEquals(E_ORDER_NOT_FOUND.getMessage(), exception.getMessage());
    }
}

