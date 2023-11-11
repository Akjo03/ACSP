package com.akjostudios.acsp.bot.web.indicator;

import com.akjostudios.acsp.bot.AcspBotApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
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
@RequiredArgsConstructor
public class ReadinessIndicator implements ReactiveHealthIndicator {
    @Qualifier("client.service.backend")
    private final WebClient backendClient;

    public Mono<Health> health() {
        return Flux.merge(
                checkBackendService(),
                checkBotReady()
        ).collectList().map(healths -> healths.stream()
                .allMatch(health -> health.getStatus().equals(Status.UP))
                ? Health.up().withDetails(healths.stream()
                        .flatMap(health -> health.getDetails().entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                ).build()
                : Health.down().withDetails(healths.stream()
                        .filter(health -> !health.getStatus().equals(Status.UP))
                        .flatMap(health -> health.getDetails().entrySet().stream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                ).build()
        );
    }

    private Mono<Health> checkBackendService() {
        return backendClient.get().uri("/actuator/health/liveness")
                .retrieve()
                .bodyToMono(String.class)
                .map(liveness -> liveness.contains("UP")
                        ? Health.up().withDetail("backendService", Status.UP).build()
                        : Health.down().withDetail("backendService", liveness).build()
                ).onErrorResume(throwable -> Mono.just(Health.down().withDetail("backendService", throwable.getMessage()).build()));
    }

    private Mono<Health> checkBotReady() {
        return AcspBotApp.getBotInstance().getStatus().equals(JDA.Status.CONNECTED)
                ? Mono.just(Health.up().withDetail("discordConnection", AcspBotApp.getBotInstance().getStatus().toString()).build())
                : Mono.just(Health.down().withDetail("discordConnection", AcspBotApp.getBotInstance().getStatus().toString()).build());
    }
}