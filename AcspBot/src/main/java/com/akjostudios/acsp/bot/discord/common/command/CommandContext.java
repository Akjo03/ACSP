package com.akjostudios.acsp.bot.discord.common.command;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CommandContext {
    public static @NotNull CommandContext of() {
        return new CommandContext();
    }
}