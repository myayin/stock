package com.ing.market.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.ing.market.dto.CreateOrderRequestDTO;
import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Customer;
import com.ing.market.model.Orders;
import com.ing.market.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceCreateOrderTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderStrategyContext orderStrategyContext;

    @InjectMocks
    private OrderService orderService;

    private Customer customer;
    private CreateOrderRequestDTO createOrderRequestDTO;
    private OrderDto orderDto;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setId(1L);

        orderDto = new OrderDto();
        createOrderRequestDTO = new CreateOrderRequestDTO();
        createOrderRequestDTO.setOrderDto(orderDto);
    }

    @Test
    void testCreateOrderSuccess() throws MarketException {
        // Act
        orderService.createOrder(createOrderRequestDTO);

        verify(orderStrategyContext, times(1)).blockAsset(orderDto);
        verify(orderRepository, times(1)).save(any(Orders.class));
    }
}
