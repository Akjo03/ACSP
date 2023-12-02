package com.akjostudios.acsp.auth.config;

import com.akjostudios.acsp.auth.properties.ExternalServiceProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@RequiredArgsConstructor
@EnableWebFlux
public class WebClientConfig {
    private final ExternalServiceProperties externalServices;

    @Bean("client.httpconnector")
    @Scope("prototype")
    public @NotNull ReactorClientHttpConnector httpConnector() {
        return new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
        );
    }

    @Bean("client.service.bot")
    public @NotNull WebClient botClient() {
        return WebClient.builder()
                .baseUrl("https://" + externalServices.getBotUrl())
                .clientConnector(httpConnector())
                .build();
    }

    @Bean("client.service.backend")
    public @NotNull WebClient backendClient() {
        return WebClient.builder()
                .baseUrl("https://" + externalServices.getBackendUrl())
                .clientConnector(httpConnector())
                .build();
    }
}