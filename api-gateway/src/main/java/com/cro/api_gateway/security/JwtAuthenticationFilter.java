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

    private final String ROLE_ADMIN = "ROLE-ADMIN";
    private final String ROLE_USER = "ROLE-USER";
    private final String ROLE_SYSTEM = "ROLE-SYSTEM";


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
                String role = claims.get("role", String.class);

                if (path.startsWith("/admin") && !ROLE_ADMIN.equals(role)) {
                    return forbidden(exchange);
                }
                if (path.startsWith("/user") && !(ROLE_USER.equals(role) || ROLE_ADMIN.equals(role))) {
                    return forbidden(exchange);
                }
                if (path.startsWith("/system") && !ROLE_SYSTEM.equals(role)) {
                    return forbidden(exchange);
                }
                ServerWebExchange modifiedExchange = exchange.mutate()
                        .request(r -> r
                                .header("X-CRO-Username", username)
                                .header("X-CRO-Role", role)
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

    private Mono<Void> forbidden(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
        return exchange.getResponse().setComplete();
    }
}
