package com.akjostudios.acsp.bot.discord.api;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public interface AcspBot {
    @NotNull JDA.Status getStatus();
    void shutdown(ConfigurableApplicationContext context, int delay);
    void restart(ConfigurableApplicationContext context, int delay);
}