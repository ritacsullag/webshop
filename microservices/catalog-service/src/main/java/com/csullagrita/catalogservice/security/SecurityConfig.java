package com.csullagrita.catalogservice.security;

import com.csullagrita.tokenlib.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String API_CATEGORY = "/api/category/**";
    public static final String API_PRODUCTS = "/api/products/**";
    public static final String ADMIN_AUTHORITY = "admin";

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
        return http
                .csrf(csrf ->
                        csrf.disable()
                )
                .sessionManagement(sessionConfig ->
                        sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(mvc.pattern(HttpMethod.POST, API_CATEGORY)).hasAuthority(ADMIN_AUTHORITY)
                                .requestMatchers(mvc.pattern(HttpMethod.PUT, API_CATEGORY)).hasAuthority(ADMIN_AUTHORITY)
                                .requestMatchers(mvc.pattern(HttpMethod.DELETE, API_CATEGORY)).hasAuthority(ADMIN_AUTHORITY)
                                .requestMatchers(mvc.pattern(HttpMethod.POST, API_PRODUCTS)).hasAuthority(ADMIN_AUTHORITY)
                                .requestMatchers(mvc.pattern(HttpMethod.PUT, API_PRODUCTS)).hasAuthority(ADMIN_AUTHORITY)
                                .requestMatchers(mvc.pattern(HttpMethod.DELETE, API_PRODUCTS)).hasAuthority(ADMIN_AUTHORITY)
                                .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build()
                ;
    }
}

