package com.ing.market.controller;

import com.ing.market.dto.BaseResponse;
import com.ing.market.dto.CreateOrderResponseDTO;
import com.ing.market.dto.DepositMoneyRequestDto;
import com.ing.market.dto.Result;
import com.ing.market.dto.WithdrawMoneyRequestDto;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Customer;
import com.ing.market.service.AssetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/v0/money", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MoneyController {

    private final AssetService assetService;

    @PostMapping("/deposit")
    public ResponseEntity<BaseResponse> depositMoney(@RequestBody DepositMoneyRequestDto request)
            throws MarketException {
        CreateOrderResponseDTO response = new CreateOrderResponseDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        request.setCustomerId(customer.getId());
        assetService.depositMoney(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<BaseResponse> withdrawMoney(@RequestBody WithdrawMoneyRequestDto request)
            throws MarketException {
        CreateOrderResponseDTO response = new CreateOrderResponseDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        request.setCustomerId(customer.getId());
        assetService.withdrawMoney(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }
}
