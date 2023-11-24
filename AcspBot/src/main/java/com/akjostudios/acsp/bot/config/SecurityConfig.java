package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.properties.ExternalServiceProperties;
import com.akjostudios.acsp.bot.web.error.ErrorHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {
    private final ExternalServiceProperties externalServices;
    private final ObjectMapper objectMapper;

    @Bean
    public @NotNull SecurityWebFilterChain filterChain(@NotNull ServerHttpSecurity http) {
        return http.authorizeExchange(exchanges -> exchanges
                        .pathMatchers("/actuator/**").permitAll()
                        .pathMatchers("/favicon.ico").permitAll()
                        .anyExchange().authenticated()
                ).httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(corsSpec -> corsSpec
                        .configurationSource(request -> {
                            CorsConfiguration corsConfig = new CorsConfiguration();
                            corsConfig.addAllowedOrigin(externalServices.getBackendUrl());
                            corsConfig.addAllowedHeader("*");
                            corsConfig.addAllowedMethod("*");
                            corsConfig.setAllowCredentials(true);
                            return corsConfig;
                        })
                ).exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec
                        .authenticationEntryPoint((exchange, e) -> handleException(exchange, e, HttpStatus.UNAUTHORIZED))
                        .accessDeniedHandler((exchange, e) -> handleException(exchange, e, HttpStatus.FORBIDDEN))
                ).build();
    }

    private Mono<Void> handleException(@NotNull ServerWebExchange exchange, @NotNull Throwable e, @NotNull HttpStatus status) {
        log.error("Security exception to " + exchange.getRequest().getPath() + " with status " + status, e);
        exchange.getResponse().setStatusCode(status);
        try {
            return exchange.getResponse().writeWith(
                    Mono.just(exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(new ErrorHandler.ErrorResponse(
                            e.getMessage(),
                            Integer.toString(status.value()),
                            exchange.getRequest().getPath().value()
                    ))))
            );
        } catch (Exception ex) {
            return exchange.getResponse().setComplete();
        }
    }
}