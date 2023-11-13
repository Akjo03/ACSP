package com.akjostudios.acsp.bot;

import com.akjostudios.acsp.bot.discord.api.AcspBot;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
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
        boolean shutdownFailed = false;

        try { acspBot.shutdown(); } catch (Exception ignored) {
            shutdownFailed = true;
        } finally { ((ConfigurableApplicationContext) context).close(); }

        if (shutdownFailed) {
            Runtime.getRuntime().halt(1);
        }
    }

    public void restart() {
        Thread restartThread = new Thread(() -> {
            log.info("Restarting ACSP Discord Bot...");
            ((ConfigurableApplicationContext) context).close();
            SpringApplication.run(AcspBotApp.class, context.getBean(ApplicationArguments.class).getSourceArgs());
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}