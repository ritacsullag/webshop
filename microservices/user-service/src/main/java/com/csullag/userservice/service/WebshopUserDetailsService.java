package com.csullag.userservice.service;

import com.csullag.userservice.model.WebshopUser;
import com.csullag.userservice.repository.WebshopUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class WebshopUserDetailsService implements UserDetailsService {

    @Autowired
    WebshopUserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebshopUser webshopUser = userRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException(username));

        return createUserDetails(webshopUser);
    }

    public static UserDetails createUserDetails(WebshopUser webshopUser) {
        return new User(webshopUser.getUsername(), webshopUser.getPassword(),
                webshopUser.getRoles().stream().map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()));
    }
}