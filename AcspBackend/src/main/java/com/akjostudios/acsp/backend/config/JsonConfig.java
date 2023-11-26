package com.akjostudios.acsp.backend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.akjo03.lib.json.JsonDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@SuppressWarnings("unused")
public class JsonConfig {
    @Bean
    @Primary
    public @NotNull ObjectMapper objectMapper() {
        return JsonDefaults.objectMapper();
    }
}