package com.ing.market.controller;

import com.ing.market.dto.GetAssetResponseDTO;
import com.ing.market.dto.Result;
import com.ing.market.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v0/asset", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AssetController {

    private final AssetService assetService;

    @GetMapping()
    public ResponseEntity<GetAssetResponseDTO> getOrders() {
        GetAssetResponseDTO response = assetService.list();
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

}
