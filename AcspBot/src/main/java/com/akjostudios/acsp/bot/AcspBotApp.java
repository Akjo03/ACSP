package com.akjostudios.acsp.bot;

import com.akjostudios.acsp.bot.discord.api.AcspBot;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Slf4j
@SuppressWarnings("unused")
public class AcspBotApp {
    private final ApplicationContext context;
    private final AcspBot acspBot;

    public static void main(String[] args) {
        SpringApplication.run(AcspBotApp.class, args);
    }

    @PreDestroy
    public void shutdown() {
        acspBot.shutdown((ConfigurableApplicationContext) context, 0);
    }
}