package com.ing.market.controller;

import com.ing.market.dto.*;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Customer;
import com.ing.market.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@Controller
@RequestMapping(value = "/v0/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<CreateOrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO request)
            throws MarketException {
        CreateOrderResponseDTO response = new CreateOrderResponseDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        request.getOrderDto().setCustomerId(customer.getId());
        orderService.createOrder(request);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<DeleteOrderResponseDTO> deleteOrder(@PathVariable Long orderId) throws MarketException {
        DeleteOrderResponseDTO response = new DeleteOrderResponseDTO();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        orderService.deleteOrder(orderId, customer.getId());
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<GetOrderResponseDTO> getOrders(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Instant endDate, Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = ((Customer) authentication.getPrincipal());
        GetOrderResponseDTO response = orderService.list(startDate, endDate, customer.getId(), pageable);
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }
}
