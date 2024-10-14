package com.ing.market.service;

import com.ing.market.model.Customer;
import com.ing.market.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return customerRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }


    public void checkAlreadyExist(String username) throws UsernameNotFoundException {
        customerRepository.findByUsername(username).ifPresent(user -> {
            throw new RuntimeException("Kullanıcı adı zaten mevcut!");
        });
    }

    public Customer saveUser(String username, String hashedPassword, String role) {
        Customer user = new Customer();
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setRole(role);
        return customerRepository.save(user);
    }
}

