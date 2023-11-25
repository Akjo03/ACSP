package com.akjostudios.acsp.bot.discord.common.command;

import com.akjostudios.acsp.bot.discord.common.command.definition.BotCommandDefinition;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public interface BotCommand {
    void execute(@NotNull CommandContext context);
    BotCommandDefinition getDefinition();
}