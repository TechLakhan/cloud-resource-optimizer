package com.cro.api_gateway.security;

import io.jsonwebtoken.Claims;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
public class JwtAuthenticationFilter {

    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public GlobalFilter validateAuthentication() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith("/auth/register") || path.startsWith("/auth/login")) {
                return chain.filter(exchange);
            }

            String authHeaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
                return unauthorized(exchange);
            }

            String token = authHeaders.substring(7);

            try {
                JwtUtil jwtUtil = new JwtUtil(secret);
                Claims claims = jwtUtil.validateToken(token);

                String username = claims.getSubject();
                String roles = claims.get("roles", String.class);

                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(r -> r
                                .header("X-CRO-Username", username)
                                .header("X-CRO-ROLE", roles)
                        )
                        .build();
                return chain.filter(modifiedExchange);
            } catch (Exception e) {
                return unauthorized(exchange);
            }
        };
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}
