package com.ing.market.service;

import static com.ing.market.constants.Constants.TRY;

import com.ing.market.config.security.JwtTokenProvider;
import com.ing.market.exception.MarketException;
import com.ing.market.model.Customer;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsServiceImpl userDetailsService;
    private final AssetService assetService;


    public String login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenProvider.createToken(authentication);
    }

    @Transactional
    public void register(String username, String password, String role) throws MarketException {
        userDetailsService.checkAlreadyExist(username);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String hashedPassword = passwordEncoder.encode(password);
        Customer customer = userDetailsService.saveUser(username, hashedPassword, role);
        if ("USER".equals(role)) {
            assetService.createAsset(customer.getId(), TRY, BigDecimal.ZERO);
        }
    }
}
