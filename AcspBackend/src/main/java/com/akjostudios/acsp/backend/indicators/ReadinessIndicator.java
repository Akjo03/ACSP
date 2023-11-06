package com.akjostudios.acsp.backend.indicators;

import com.akjostudios.acsp.backend.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReadinessIndicator implements ReactiveHealthIndicator {
    private final WebClientConfig serviceClients;

    public Mono<Health> health() {
        return checkBotService();
    }

    private @NotNull Mono<Health> checkBotService() {
        return Mono.just(Health.up().build());
    }
}