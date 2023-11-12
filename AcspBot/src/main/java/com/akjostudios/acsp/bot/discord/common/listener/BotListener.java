package com.akjostudios.acsp.bot.discord.common.listener;

import net.dv8tion.jda.api.events.GenericEvent;
import org.springframework.stereotype.Component;

@Component
@FunctionalInterface
public interface BotListener<T extends GenericEvent> {
    void onEvent(T event);
}