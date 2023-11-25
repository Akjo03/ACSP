package com.akjostudios.acsp.backend.health;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReadinessIndicator implements ReactiveHealthIndicator {
    @Qualifier("client.service.bot")
    private final WebClient botClient;

    public Mono<Health> health() {
        return checkBotService();
    }

    private @NotNull Mono<Health> checkBotService() {
        return botClient.get().uri("/actuator/health/liveness")
                .retrieve()
                .bodyToMono(String.class)
                .map(liveness -> liveness.contains("UP")
                        ? Health.up().withDetail("botService", Status.UP).build()
                        : Health.down().withDetail("botService", liveness).build()
                ).onErrorResume(throwable -> Mono.just(Health.down().withDetail("botService", throwable.getMessage()).build()));
    }
}