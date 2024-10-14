package com.ing.market.config;

import com.ing.market.model.enums.SideType;
import com.ing.market.service.AssetService;
import com.ing.market.service.BuyOrderStrategy;
import com.ing.market.service.OrderStrategy;
import com.ing.market.service.OrderStrategyContext;
import com.ing.market.service.SellOrderStrategy;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderStrategyConfig {

    @Bean
    public Map<String, OrderStrategy> orderStrategies(AssetService assetService) {
        Map<String, OrderStrategy> strategies = new HashMap<>();
        strategies.put(SideType.BUY.name(), new BuyOrderStrategy(assetService));
        strategies.put(SideType.SELL.name(), new SellOrderStrategy(assetService));
        return strategies;
    }

    @Bean
    public OrderStrategyContext orderStrategyContext(Map<String, OrderStrategy> strategies) {
        return new OrderStrategyContext(strategies);
    }
}
