package com.akjostudios.acsp.bot.discord.common.primitive;

import com.akjostudios.acsp.bot.discord.impl.AcspBotImpl;

@SuppressWarnings("unused")
public interface DiscordPrimitive {
    default long getId() {
        return switch (AcspBotImpl.getEnvironment()) {
            case LOCAL -> getLocalId();
            case PROD -> getProdId();
        };
    }

    long getLocalId();
    long getProdId();
}