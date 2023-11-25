package com.akjostudios.acsp.bot.web.health;

import com.akjostudios.acsp.bot.discord.api.AcspBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
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
@RequiredArgsConstructor
public class ReadinessIndicator implements ReactiveHealthIndicator {
    private final AcspBot acspBot;

    @Qualifier("client.service.backend")
    private final WebClient backendClient;

    public Mono<Health> health() {
        return Flux.merge(
                checkBackendService(),
                checkBotReady()
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

    private @NotNull Mono<Health> checkBackendService() {
        return backendClient.get().uri("/actuator/health/liveness")
                .retrieve()
                .bodyToMono(String.class)
                .map(liveness -> liveness.contains("UP")
                        ? Health.up().withDetail("backendService", Status.UP).build()
                        : Health.down().withDetail("backendService", liveness).build()
                ).onErrorResume(throwable -> Mono.just(Health.down().withDetail("backendService", throwable.getMessage()).build()));
    }

    private @NotNull Mono<Health> checkBotReady() {
        return acspBot.getStatus().equals(JDA.Status.CONNECTED)
                ? Mono.just(Health.up().withDetail("discordConnection", acspBot.getStatus().toString()).build())
                : Mono.just(Health.down().withDetail("discordConnection", acspBot.getStatus().toString()).build());
    }
}