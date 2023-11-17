package com.akjostudios.acsp.bot.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.actuate.health.HealthContributorRegistry;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class HealthMetricExportConfig {
    @Bean
    public MeterRegistryCustomizer<MeterRegistry> healthRegistryCustomizer(@NotNull HealthContributorRegistry healthRegistry) {
        return registry -> healthRegistry.stream().forEach(
            healthContributor -> registry
                .gauge("health", Tags.of("name", healthContributor.getName()), healthRegistry, health -> getStatusCode(
                        ((HealthIndicator) health.getContributor(healthContributor.getName())).getHealth(false).getStatus()
                ))
        );
    }

    private int getStatusCode(@NotNull Status status) {
        if (status.equals(Status.UP)) return 1;
        if (status.equals(Status.OUT_OF_SERVICE)) return -1;
        if (status.equals(Status.DOWN)) return 0;
        return -2;
    }
}