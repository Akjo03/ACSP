package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.definition.BotDefinition;
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
public class BotDefinitionConfig {
    private final BotDefinition botDefinition;

    @Autowired
    public BotDefinitionConfig(
            @Value("classpath:bot_definition.json") @NotNull Resource botDefinitionFile,
            @NotNull ObjectMapper objectMapper
    ) {
        try {
            botDefinition = objectMapper.readValue(botDefinitionFile.getURL(), BotDefinition.class);
        } catch (IOException e) {
            log.error("Failed to load bot definition from file!", e);
            throw new RuntimeException(e);
        }
    }

    @Bean
    public BotDefinition botDefinition() { return botDefinition; }
}