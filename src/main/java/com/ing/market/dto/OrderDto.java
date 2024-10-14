package com.ing.market.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto implements Serializable {
    private long customerId;
    private String assetName;
    private String side;
    private BigDecimal size;
    private BigDecimal price;
}
