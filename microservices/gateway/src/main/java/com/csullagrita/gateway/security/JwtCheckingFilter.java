package com.csullagrita.gateway.security;

import com.csullagrita.tokenlib.JwtAuthFilter;
import com.csullagrita.tokenlib.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.PathContainer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.pattern.PathPattern;
import org.springframework.web.util.pattern.PathPatternParser;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Component
public class JwtCheckingFilter implements GlobalFilter {

    @Autowired
    private JwtService jwtService;

    private PathPattern loginPathPattern = PathPatternParser.defaultInstance.parse("/users/login");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        Set<URI> origUrls = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR);
        URI originalUri = origUrls.iterator().next();

        if (loginPathPattern.matches(PathContainer.parsePath(originalUri.toString()).subPath(4)))
            return chain.filter(exchange);

        List<String> authHeaders = exchange.getRequest().getHeaders().get("Authorization");

        if (ObjectUtils.isEmpty(authHeaders))
            return send401Response(exchange);
        else {
            String authHeader = authHeaders.get(0);
            UsernamePasswordAuthenticationToken userDetails = null;
            try {
                userDetails = JwtAuthFilter.createUserDetailsFromAuthHeader(authHeader, jwtService);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (userDetails == null)
                return send401Response(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> send401Response(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}