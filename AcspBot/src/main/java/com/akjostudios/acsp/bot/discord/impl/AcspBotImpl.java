package com.akjostudios.acsp.bot.discord.impl;

import com.akjostudios.acsp.bot.AcspBotApp;
import com.akjostudios.acsp.bot.discord.api.AcspBot;
import com.akjostudios.acsp.bot.discord.common.listener.CommonListener;
import com.akjostudios.acsp.bot.properties.BotConfigProperties;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AcspBotImpl implements AcspBot {
    private final JDA botInstance;

    @Autowired
    public AcspBotImpl(@NotNull ApplicationContext context, @NotNull BotConfigProperties properties) {
        log.info("Starting ACSP Discord Bot in {} environment...", properties.getEnvironment());

        JDABuilder builder = JDABuilder.createDefault(properties.getBotToken())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .setMemberCachePolicy(MemberCachePolicy.ALL);

        builder.addEventListeners(context.getBean(CommonListener.class));

        botInstance = builder.build();
    }

    @Override
    public @NotNull JDA.Status getStatus() {
        return botInstance.getStatus();
    }

    @Override
    public void shutdown(ConfigurableApplicationContext context, int delay) {
        boolean shutdownFailed = false;

        try (context) { botInstance.shutdownNow(); } catch (Exception ignored) {
            shutdownFailed = true;
        }

        if (shutdownFailed) { Runtime.getRuntime().halt(1); }
    }

    @Override
    public void restart(ConfigurableApplicationContext context, int delay) {
        Thread restartThread = new Thread(() -> {
            log.info("Restarting ACSP Discord Bot...");
            context.close();
            SpringApplication.run(AcspBotApp.class, context.getBean(ApplicationArguments.class).getSourceArgs());
        });
        restartThread.setDaemon(false);
        restartThread.start();
    }
}