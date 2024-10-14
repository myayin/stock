package com.ing.market.service;

import com.ing.market.dto.CreateOrderRequestDTO;
import com.ing.market.dto.GetOrderResponseDTO;
import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;
import com.ing.market.mapper.OrderMapper;
import com.ing.market.model.Orders;
import com.ing.market.model.enums.Status;
import com.ing.market.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ing.market.constants.ResponseCode.E_ORDER_NOT_FOUND;

@AllArgsConstructor
@Service
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStrategyContext orderStrategyContext;


    @Transactional
    public void createOrder(CreateOrderRequestDTO request) throws MarketException {
        orderStrategyContext.blockAsset(request.getOrderDto());
        Orders order = OrderMapper.INSTANCE.toEntity(request.getOrderDto());
        order.setStatus(Status.PENDING.name());
        order.setCreateDate(new Date().toInstant());
        orderRepository.save(order);
    }

    @Transactional
    public void deleteOrder(Long orderId, Long customerId) throws MarketException {
        Orders order = orderRepository.findByIdAndStatusAndCustomerId(orderId, Status.PENDING.name(), customerId)
                .orElseThrow(() -> new MarketException(E_ORDER_NOT_FOUND.name(), E_ORDER_NOT_FOUND.getMessage()));
        OrderDto orderDto = OrderMapper.INSTANCE.toDto(order);
        orderStrategyContext.unblockAsset(orderDto);
        order.setStatus(Status.CANCELED.name());
        orderRepository.save(order);
    }

    public GetOrderResponseDTO list(Instant startDate, Instant endDate, Long customerId, Pageable pageable) {
        List<Orders> orders = orderRepository.findByCustomerIdAndStatusAndCreateDateBetween(customerId,
                Status.PENDING.name(), startDate, endDate, pageable);
        List<OrderDto> orderDtos = orders.stream().map(OrderMapper.INSTANCE::toDto).collect(Collectors.toList());
        GetOrderResponseDTO responseDTO = new GetOrderResponseDTO();
        responseDTO.setOrderDtos(orderDtos);
        return responseDTO;
    }
}
