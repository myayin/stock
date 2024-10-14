package com.ing.market.service;

import static com.ing.market.constants.Constants.TRY;

import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;
import java.math.BigDecimal;

public class BuyOrderStrategy implements OrderStrategy {

    private final AssetService assetService;

    public BuyOrderStrategy(AssetService assetService) {
        this.assetService = assetService;
    }

    @Override
    public void blockAsset(OrderDto orderDto) throws MarketException {
        assetService.blockAsset(orderDto.getCustomerId(), TRY, getTotalPriceInTry(orderDto));
    }

    @Override
    public void unblockAsset(OrderDto orderDto) throws MarketException {
        assetService.unblockAsset(orderDto.getCustomerId(), TRY, getTotalPriceInTry(orderDto));
    }

    private static BigDecimal getTotalPriceInTry(OrderDto orderDto) {
        return orderDto.getSize().multiply(orderDto.getPrice());
    }

}
