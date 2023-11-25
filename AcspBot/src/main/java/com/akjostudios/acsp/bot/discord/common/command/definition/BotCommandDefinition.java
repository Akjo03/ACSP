package com.akjostudios.acsp.bot.discord.common.command.definition;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public class BotCommandDefinition {
    private final String commandName;

    protected BotCommandDefinition(
            @NotNull String commandName
    ) {
        this.commandName = commandName;
    }
}