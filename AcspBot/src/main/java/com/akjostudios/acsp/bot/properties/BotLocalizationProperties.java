package com.akjostudios.acsp.bot.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.localization")
@Getter
@Setter
public class BotLocalizationProperties {
    private String resourceName;
    private String defaultLocale;
}