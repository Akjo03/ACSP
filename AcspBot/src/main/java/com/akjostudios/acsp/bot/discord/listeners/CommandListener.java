package com.akjostudios.acsp.bot.discord.listeners;

import com.akjostudios.acsp.bot.definition.BotDefinition;
import com.akjostudios.acsp.bot.discord.common.BotEventType;
import com.akjostudios.acsp.bot.discord.common.command.BotCommand;
import com.akjostudios.acsp.bot.discord.common.command.definition.BotCommandDefinition;
import com.akjostudios.acsp.bot.discord.common.listener.BotListener;
import com.akjostudios.acsp.bot.discord.service.BotCommandService;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CommandListener implements BotListener<MessageReceivedEvent> {
    private final BotCommandService commandService;

    private final BotDefinition botDefinition;

    private final Map<BotCommand, BotCommandDefinition> commands;

    @Autowired
    public CommandListener(
            @NotNull BotCommandService commandService,
            @NotNull BotDefinition botDefinition,
            @NotNull List<BotCommand> commands
    ) {
        this.commandService = commandService;
        this.botDefinition = botDefinition;
        this.commands = commands.stream()
                .collect(HashMap::new, (map, command) -> map.put(
                        command,
                        command.getDefinition()),
                        HashMap::putAll
                );
    }

    @Override
    public void onEvent(BotEventType type, MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) { return; }
        if (!event.isFromGuild()) { return; }

        String commandPrefix = botDefinition.getCommandPrefix();
        if (!event.getMessage().getContentRaw().startsWith(commandPrefix)) { return; }

        String commandStr = event.getMessage().getContentRaw().substring(commandPrefix.length());

        List<String> commandParts = List.of(commandStr.split(" "));
        if (commandParts.isEmpty()) { return; }

        String commandName = commandParts.get(0);
        List<String> commandArguments = commandParts.subList(1, commandParts.size());

        commands.keySet().stream()
                .filter(command -> command.getDefinition().getCommandName().equals(commandName))
                .forEach(command -> command.execute(commandService.getCommandContext(
                        command.getDefinition(),
                        commandArguments,
                        event.getAuthor(),
                        event.getChannel()
                )));
    }
}