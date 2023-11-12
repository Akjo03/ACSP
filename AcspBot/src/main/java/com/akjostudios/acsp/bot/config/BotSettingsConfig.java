package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.settings.BotSettings;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
@Slf4j
@SuppressWarnings("unused")
public class BotSettingsConfig {
    private final BotSettings botSettings;

    @Autowired
    public BotSettingsConfig(
            @Value("classpath:bot_settings.json") @NotNull Resource botSettingsFile,
            @NotNull ObjectMapper objectMapper
    ) {
        try {
            botSettings = objectMapper.readValue(botSettingsFile.getURL(), BotSettings.class);
        } catch (IOException e) {
            log.error("Failed to load bot settings from file!", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public BotSettings botSettings() { return botSettings; }
}