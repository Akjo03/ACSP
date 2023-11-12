package com.akjostudios.acsp.bot.discord.api;

import net.dv8tion.jda.api.JDA;

public interface AcspBot {
    JDA.Status getStatus();
    void shutdown();
}