package com.cro.api_gateway.security;

import com.cro.api_gateway.dto.ApiErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Configuration
public class JwtAuthenticationFilter {

    private static final String ROLE_ADMIN = "ROLE-ADMIN";
    private static final String ROLE_USER = "ROLE-USER";
    private static final String ROLE_SYSTEM = "ROLE-SYSTEM";
    private static final String X_CRO_USERNAME = "X-CRO-Username";


    @Value("${jwt.secret}")
    private String secret;

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public GlobalFilter validateAuthentication() {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getURI().getPath();

            if (path.startsWith("/auth/register") || path.startsWith("/auth/login") || path.startsWith("/resource/health") || path.startsWith("/auth/health")) {
                return chain.filter(exchange);
            }

            String authHeaders = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeaders == null || !authHeaders.startsWith("Bearer ")) {
                return unauthorized(exchange, "missing or invalid authorization header");
            }

            String token = authHeaders.substring(7);

            Claims claims;

            try {
                JwtUtil jwtUtil = new JwtUtil(secret);
                claims = jwtUtil.validateToken(token);
            } catch (Exception e) {
                return unauthorized(exchange, "Invalid JWT token");
            }

            String tokenUsername = claims.getSubject();
            String requestUsername = exchange.getRequest().getHeaders().getFirst(X_CRO_USERNAME);

            String role = claims.get("role", String.class);

            if (requestUsername == null || StringUtils.isBlank(requestUsername)) {
                return unauthorized(exchange, "missing or invalid header X-CRO-Username");
            }

            if (!requestUsername.equals(tokenUsername)) {
                return unauthorized(exchange, "X-CRO-Username header mismatch in request & token");
            }

            if (path.startsWith("/admin") && !ROLE_ADMIN.equals(role)) {
                return forbidden(exchange, "Admin access required");
            }
            if (path.startsWith("/user") && !(ROLE_USER.equals(role) || ROLE_ADMIN.equals(role))) {
                return forbidden(exchange, "user access required");
            }
            if (path.startsWith("/system") && !ROLE_SYSTEM.equals(role)) {
                return forbidden(exchange, "system access required");
            }
            return chain.filter(exchange);
        };
    }


    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiErrorResponse errorResponse = new ApiErrorResponse(
                401,
                "UNAUTHORIZED",
                message,
                exchange.getRequest().getPath().value(),
                Instant.now()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorResponse);
            DataBuffer buffer = exchange.getResponse()
                    .bufferFactory()
                    .wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(buffer));
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }


    }

    private Mono<Void> forbidden(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

        ApiErrorResponse response = new ApiErrorResponse(
                403,
                "FORBIDDEN",
                message,
                exchange.getRequest().getPath().value(),
                Instant.now()
        );

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(response);
            DataBuffer wrap = exchange.getResponse()
                    .bufferFactory().wrap(bytes);

            return exchange.getResponse().writeWith(Mono.just(wrap));
        }
        catch (Exception e) {
            return exchange.getResponse().setComplete();
        }
    }
}
