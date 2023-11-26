package com.akjostudios.acsp.bot.discord.common.command;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@FunctionalInterface
public interface BotCommand {
    void execute(@NotNull CommandContext context);

    default String getName() {
        return StringUtils.removeEnd(this.getClass().getSimpleName(), "Command").toLowerCase();
    }
}