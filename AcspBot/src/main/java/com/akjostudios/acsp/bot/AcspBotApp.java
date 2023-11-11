package com.akjostudios.acsp.bot;

import com.akjostudios.acsp.bot.discord.AcspBot;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Log4j2
public class AcspBotApp {
    private final ApplicationContext context;
    private final AcspBot acspBot;

    public static void main(String[] args) {
        SpringApplication.run(AcspBotApp.class, args);
    }

    @PreDestroy
    public void shutdown() {
        boolean shutdownFailed = false;

        try { acspBot.shutdown(); } catch (Exception ignored) {
            shutdownFailed = true;
        } finally { ((ConfigurableApplicationContext) context).close(); }

        if (shutdownFailed) {
            Runtime.getRuntime().halt(1);
        }
    }
}