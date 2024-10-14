package com.ing.market.controller;

import com.ing.market.dto.*;
import com.ing.market.exception.MarketException;
import com.ing.market.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/v0/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/verifyPin")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequestDto loginRequest) {
        String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequestDto registerRequest)
            throws MarketException {
        loginService.register(registerRequest.getUsername(), registerRequest.getPassword(), "USER");
        BaseResponse response = new BaseResponse();
        response.setResult(Result.success());
        return ResponseEntity.ok(response);
    }
}
