package com.akjostudios.acsp.bot.properties;

import com.akjostudios.acsp.bot.util.BotEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.config")
@Getter
@Setter
public class ConfigProperties {
    private BotEnvironment environment;
}