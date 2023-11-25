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
public class BotSubcommand implements Constructable {
    @Contract(" -> new")
    public static @NotNull BotSubcommandConstructor constructor() {
        return new BotSubcommandConstructor();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BotSubcommandConstructor implements Constructor<BotSubcommand, BotSubcommandConstructor> {
        @Override
        public @NotNull Result<BotSubcommand> build() {
            return Result.success(new BotSubcommand());
        }

        @Override
        public @NotNull Result<BotSubcommand> validate(@NotNull BotSubcommand botSubcommand) {
            return Result.success(botSubcommand);
        }
    }
}