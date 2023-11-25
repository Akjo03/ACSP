package com.akjostudios.acsp.bot.discord.commands;

import com.akjostudios.acsp.bot.discord.common.command.BotCommand;
import com.akjostudios.acsp.bot.discord.common.command.CommandContext;
import com.akjostudios.acsp.bot.discord.common.command.definition.BotCommandDefinition;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeginCommand extends BotCommandDefinition implements BotCommand {
    protected BeginCommand() {
        super("begin");
    }

    @Override
    public void execute(@NotNull CommandContext context) {
        log.info("Begin command executed!");
    }

    @Override
    public BotCommandDefinition getDefinition() { return this; }
}