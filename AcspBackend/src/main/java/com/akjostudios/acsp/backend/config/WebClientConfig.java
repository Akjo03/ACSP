package com.akjostudios.acsp.backend.config;

import com.akjostudios.acsp.backend.properties.ExternalServiceProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@EnableWebFlux
public class WebClientConfig {
    private final ExternalServiceProperties externalServices;

    @Bean("client.service.bot")
    public WebClient botClient() {
        return WebClient.create(externalServices.getBotUrl());
    }
}