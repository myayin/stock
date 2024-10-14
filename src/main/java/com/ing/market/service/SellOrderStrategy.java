package com.ing.market.service;

import com.ing.market.dto.OrderDto;
import com.ing.market.exception.MarketException;

public class SellOrderStrategy implements OrderStrategy {

    private final AssetService assetService;

    public SellOrderStrategy(AssetService assetService) {
        this.assetService = assetService;
    }

    public void blockAsset(OrderDto orderDto) throws MarketException {
        assetService.blockAsset(orderDto.getCustomerId(), orderDto.getAssetName(), orderDto.getSize());
    }

    public void unblockAsset(OrderDto orderDto) throws MarketException {
        assetService.unblockAsset(orderDto.getCustomerId(), orderDto.getAssetName(), orderDto.getSize());
    }

}
