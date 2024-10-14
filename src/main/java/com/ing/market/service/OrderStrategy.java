package com.ing.market.service;

import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;

public interface OrderStrategy {

    void blockAsset(OrderDto orderDto) throws MarketException;

    void unblockAsset(OrderDto request) throws MarketException;
}
