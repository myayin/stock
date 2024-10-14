package com.ing.market.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateOrderRequestDTO implements Serializable {

    private OrderDto orderDto;
}
