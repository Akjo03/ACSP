package com.akjostudios.acsp.bot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.services")
@Getter
@Setter
public class ExternalServiceProperties {
    private String backendUrl;
    private String authUrl;
}