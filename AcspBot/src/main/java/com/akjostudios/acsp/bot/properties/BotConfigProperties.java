package com.akjostudios.acsp.bot.properties;

import com.akjostudios.acsp.bot.discord.common.BotEnvironment;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.config")
@Getter
@Setter
public class BotConfigProperties {
    private BotEnvironment environment;
    private String clientId;
    private String clientSecret;
    private String botToken;
}