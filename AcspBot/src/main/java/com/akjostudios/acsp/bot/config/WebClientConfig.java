package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.properties.ExternalServiceProperties;
import lombok.RequiredArgsConstructor;
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
    public ReactorClientHttpConnector httpConnector() {
        return new ReactorClientHttpConnector(
                HttpClient.create().followRedirect(true)
        );
    }

    @Bean("client.service.backend")
    public WebClient backendClient() {
        return WebClient.builder()
                .baseUrl("https://" + externalServices.getBackendUrl())
                .clientConnector(httpConnector())
                .build();
    }
}