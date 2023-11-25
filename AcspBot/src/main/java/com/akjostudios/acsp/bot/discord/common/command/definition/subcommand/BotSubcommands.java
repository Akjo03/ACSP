package com.akjostudios.acsp.bot.discord.common.command.definition.subcommand;

import io.github.akjo03.lib.constructor.Constructable;
import io.github.akjo03.lib.constructor.Constructor;
import io.github.akjo03.lib.result.Result;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@SuppressWarnings("unused")
public class BotSubcommands implements Constructable {
    @Contract(" -> new")
    public static @NotNull BotSubcommandsConstructor constructor() {
        return new BotSubcommandsConstructor();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BotSubcommandsConstructor implements Constructor<BotSubcommands, BotSubcommandsConstructor> {
        @Override
        public @NotNull Result<BotSubcommands> build() {
            return Result.success(new BotSubcommands());
        }

        @Override
        public @NotNull Result<BotSubcommands> validate(@NotNull BotSubcommands botSubcommands) {
            return Result.success(botSubcommands);
        }
    }
}