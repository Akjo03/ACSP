package com.akjostudios.acsp.bot;

import com.akjostudios.acsp.bot.properties.BotEnvironmentProperties;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@ConfigurationPropertiesScan
@RequiredArgsConstructor
@Getter
@Log4j2
public class AcspBotApp implements ApplicationRunner {
    private static ConfigurableApplicationContext context;
    @Getter private static JDA botInstance;

    private final BotEnvironmentProperties properties;

    public static void main(String[] args) {
        context = SpringApplication.run(AcspBotApp.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("---------------------------------------------------------------");

        if (properties.getEnvironment() == null) {
            log.error("Environment not set. Please set the environment using a profile.");
            SpringApplication.exit(context, () -> 1);
            return;
        }

        log.info("Starting ACSP Discord Bot in {} environment...", properties.getEnvironment());

        JDABuilder builder = JDABuilder.createDefault(properties.getBotToken())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));
        botInstance = builder.build().awaitReady();
    }

    @PreDestroy
    public static void shutdown() {
        boolean shutdownFailed = false;

        try { botInstance.shutdownNow(); } catch (Exception ignored) {
            shutdownFailed = true;
        } finally { context.close(); }

        if (shutdownFailed) {
            Runtime.getRuntime().halt(1);
        }
    }

    public static void restart() {
        Thread restartThread = new Thread(() -> {
            log.info("Restarting ACSP Discord Bot...");
            context.close();
            SpringApplication.run(AcspBotApp.class, context.getBean(ApplicationArguments.class).getSourceArgs());
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}