package com.akjostudios.acsp.bot.controller;

import com.akjostudios.acsp.bot.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("health")
@RequiredArgsConstructor
public class HealthController implements ReactiveHealthIndicator {
    private final WebClientConfig serviceClients;

    public Mono<Health> health() {
        return Mono.just(Health.up().build());
    }
}