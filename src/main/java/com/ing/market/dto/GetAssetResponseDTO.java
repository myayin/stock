package com.ing.market.dto;

import java.io.Serializable;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetAssetResponseDTO extends BaseResponse implements Serializable {

    private List<AssetDto> assetDtos;
}
