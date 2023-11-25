package com.akjostudios.acsp.bot.discord.service;

import com.akjostudios.acsp.bot.discord.common.command.CommandContext;
import com.akjostudios.acsp.bot.discord.common.command.definition.BotCommandDefinition;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BotCommandService {
    public @NotNull CommandContext getCommandContext(
            @NotNull BotCommandDefinition commandDefinition,
            @NotNull List<String> commandArguments,
            @NotNull User author,
            @NotNull MessageChannelUnion channel
    ) {
        return CommandContext.of(

        );
    }
}