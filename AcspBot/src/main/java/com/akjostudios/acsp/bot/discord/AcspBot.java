package com.akjostudios.acsp.bot.discord;

import com.akjostudios.acsp.bot.properties.BotEnvironmentProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class AcspBot {
    private final JDA botInstance;

    @Autowired
    public AcspBot(@NotNull ApplicationContext context, @NotNull BotEnvironmentProperties properties) {
        log.info("Starting ACSP Discord Bot in {} environment...", properties.getEnvironment());

        JDABuilder builder = JDABuilder.createDefault(properties.getBotToken())
                .setEnabledIntents(GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));

        botInstance = builder.build();
    }

    public void shutdown() {
        botInstance.shutdownNow();
    }
}