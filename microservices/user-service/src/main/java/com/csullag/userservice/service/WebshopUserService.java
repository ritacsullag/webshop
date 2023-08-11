package com.csullag.userservice.service;

import com.csullag.userservice.dto.LoginDto;
import com.csullag.userservice.model.WebshopUser;
import com.csullag.userservice.repository.WebshopUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WebshopUserService {
    private static final String CUSTOMER_ROLE = "customer";

    private final WebshopUserRepository webshopUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final FacebookLoginService facebookLoginService;


    public WebshopUser findById(long id) {
        return webshopUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User does not exist"));
    }

    public WebshopUser createWebshopUser(WebshopUser webshopUser) throws Exception {
        if (Boolean.TRUE.equals(webshopUserRepository.existsByUsername(webshopUser.getUsername()))) {
            throw new Exception("Username already exist");
        }

        webshopUser.setRoles(Set.of(CUSTOMER_ROLE));
        String encodedPassword = passwordEncoder.encode(webshopUser.getPassword());
        webshopUser.setPassword(encodedPassword);
        return webshopUserRepository.save(webshopUser);
    }

    public String login(LoginDto loginDto) {
        UserDetails userDetails;
        if (loginDto.getFacebookToken() == null || loginDto.getFacebookToken().isEmpty()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

            userDetails = (UserDetails) authentication.getPrincipal();
        } else {
            userDetails = facebookLoginService.getUserDetailsForToken(loginDto.getFacebookToken());
        }

//        return "\"" + jwtService.creatJwtToken(userDetails) + "\"";
        return "null";
    }
}
