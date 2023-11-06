package com.akjostudios.acsp.bot.indicators;

import com.akjostudios.acsp.bot.config.WebClientConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReadinessIndicator implements ReactiveHealthIndicator {
    private final WebClientConfig serviceClients;

    public Mono<Health> health() {
        return Mono.zip(
                checkBackendService(),
                checkBotReady()
        ).map(tuple -> {
            Health.Builder builder = Health.up();
            builder = tuple.getT1().getStatus().equals(Status.DOWN)
                    ? builder.down().withDetail("backendService", tuple.getT1().getStatus().toString() + " " + tuple.getT1().getDetails().toString())
                    : builder.up();
            builder = tuple.getT2().getStatus().equals(Status.DOWN)
                    ? builder.down().withDetail("discordConnection", tuple.getT2().getStatus().toString() + " " + tuple.getT2().getDetails().toString())
                    : builder.up();
            return builder.build();
        });
    }

    private Mono<Health> checkBackendService() {
        return Mono.just(Health.up().build());
    }

    private Mono<Health> checkBotReady() {
        return Mono.just(Health.up().build());
    }
}