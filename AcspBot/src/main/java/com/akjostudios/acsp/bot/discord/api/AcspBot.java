package com.akjostudios.acsp.bot.discord.api;

import net.dv8tion.jda.api.JDA;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface AcspBot {
    @NotNull JDA.Status getStatus();
    void shutdown();
}