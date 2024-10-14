package com.ing.market.mapper;

import com.ing.market.dto.AssetDto;
import com.ing.market.model.Asset;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AssetMapper {

    AssetMapper INSTANCE = Mappers.getMapper(AssetMapper.class);

    AssetDto toDto(Asset asset);
}
