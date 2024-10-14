package com.ing.market.dto;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDto {

    private String assetName;

    private BigDecimal size;

    private BigDecimal usableSize;
}
