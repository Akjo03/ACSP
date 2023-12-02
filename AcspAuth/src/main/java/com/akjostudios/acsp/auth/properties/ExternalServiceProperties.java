package com.akjostudios.acsp.auth.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.services")
@Getter
@Setter
public class ExternalServiceProperties {
    private String botUrl;
    private String backendUrl;
}