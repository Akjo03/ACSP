package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.definition.BotDefinition;
import com.akjostudios.acsp.bot.definition.BotDefinitionValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.akjo03.lib.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@Slf4j
@SuppressWarnings("unused")
public class BotDefinitionConfig {
    private final BotDefinition botDefinition;

    @Autowired
    public BotDefinitionConfig(
            @Value("classpath:bot_definition.json") @NotNull Resource botDefinitionFile,
            @NotNull ObjectMapper objectMapper,
            @NotNull BotDefinitionValidator botDefinitionValidator
    ) {
        botDefinition = Result.from(() -> objectMapper.readValue(botDefinitionFile.getURL(), BotDefinition.class))
                .flatMap(botDefinitionValidator::validate)
                .ifError(error -> log.error("Error while validating bot definition: {}", error.getMessage()))
                .orElseThrow(() -> new RuntimeException("Error while validating bot definition"));
    }

    @Bean
    public BotDefinition botDefinition() { return botDefinition; }
}