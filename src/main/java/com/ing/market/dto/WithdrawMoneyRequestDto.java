package com.ing.market.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WithdrawMoneyRequestDto extends BaseResponse implements Serializable {
    private long customerId;
    private BigDecimal amount;
    private String iban;
}
