package com.ing.market.service;

import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;
import java.util.Map;

public class OrderStrategyContext {

    private final Map<String, OrderStrategy> strategies;

    public OrderStrategyContext(Map<String, OrderStrategy> strategies) {
        this.strategies = strategies;
    }

    public void blockAsset(OrderDto orderDto) throws MarketException {
        OrderStrategy strategy = strategies.get(orderDto.getSide());
        strategy.blockAsset(orderDto);
    }

    public void unblockAsset(OrderDto orderDto) throws MarketException {
        OrderStrategy strategy = strategies.get(orderDto.getSide());
        strategy.unblockAsset(orderDto);
    }
}
