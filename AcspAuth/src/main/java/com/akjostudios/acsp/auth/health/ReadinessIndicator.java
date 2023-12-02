package com.akjostudios.acsp.auth.health;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ReadinessIndicator implements ReactiveHealthIndicator {
    private final WebClient botClient;
    private final WebClient backendClient;

    public ReadinessIndicator(
            @Qualifier("client.service.bot") WebClient botClient,
            @Qualifier("client.service.backend") WebClient backendClient
    ) {
        this.botClient = botClient;
        this.backendClient = backendClient;
    }

    public Mono<Health> health() {
        return Flux.merge(
                checkBackendService(),
                checkBotService()
        ).collectList().map(healths -> {
            Map<String, Object> details = healths.stream()
                    .flatMap(health -> health.getDetails().entrySet().stream())
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2));
            return healths.stream()
                    .anyMatch(health -> health.getStatus().equals(Status.DOWN))
                    ? Health.down().withDetails(details).build()
                    : Health.up().withDetails(details).build();
        });
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

    private @NotNull Mono<Health> checkBackendService() {
        return backendClient.get().uri("/actuator/health/liveness")
                .retrieve()
                .bodyToMono(String.class)
                .map(liveness -> liveness.contains("UP")
                        ? Health.up().withDetail("backendService", Status.UP).build()
                        : Health.down().withDetail("backendService", liveness).build()
                ).onErrorResume(throwable -> Mono.just(Health.down().withDetail("backendService", throwable.getMessage()).build()));
    }
}