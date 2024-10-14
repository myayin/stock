package com.ing.market.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepositMoneyRequestDto {
    private BigDecimal amount;
    private long customerId;
}
