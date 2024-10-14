package com.ing.market.mapper;

import com.ing.market.dto.OrderDto;
import com.ing.market.model.Orders;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDto toDto(Orders user);

    @Mapping(source = "side", target = "side")
    Orders toEntity(OrderDto userDTO);
}
