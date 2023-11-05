package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.properties.ExternalServiceProperties;
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

    @Value("${PORT}")
    private String port;

    @Bean("client.service.backend")
    public WebClient backendClient() {
        return WebClient.create(externalServices.getBackendUrl().concat(":").concat(port));
    }
}