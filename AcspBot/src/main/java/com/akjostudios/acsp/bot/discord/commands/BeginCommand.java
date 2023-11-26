package com.akjostudios.acsp.bot.discord.commands;

import com.akjostudios.acsp.bot.discord.common.command.BotCommand;
import com.akjostudios.acsp.bot.discord.common.command.CommandContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BeginCommand implements BotCommand {
    @Override
    public void execute(@NotNull CommandContext context) {
        log.info("Begin command executed!");
    }
}