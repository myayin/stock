package com.ing.market.controller;

import com.ing.market.dto.*;
import com.ing.market.exception.MarketException;
import com.ing.market.service.LoginService;
import com.ing.market.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/v0/admin", produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {

    private final LoginService loginService;
    private final OrderService orderService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequestDto registerRequest)
            throws MarketException {
        loginService.register(registerRequest.getUsername(), registerRequest.getPassword(), "ADMIN");
        BaseResponse response = new BaseResponse();
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO request)
            throws MarketException {
        CreateOrderResponseDTO response = new CreateOrderResponseDTO();
        orderService.createOrder(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }
}
