package com.akjostudios.acsp.bot.discord.common.command.definition.argument;

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
public class BotCommandArgument implements Constructable {
    @Contract(" -> new")
    public static @NotNull BotCommandArgumentConstructor constructor() {
        return new BotCommandArgumentConstructor();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BotCommandArgumentConstructor implements Constructor<BotCommandArgument, BotCommandArgumentConstructor> {
        @Override
        public @NotNull Result<BotCommandArgument> build() {
            return Result.success(new BotCommandArgument());
        }

        @Override
        public @NotNull Result<BotCommandArgument> validate(@NotNull BotCommandArgument botCommandArgument) {
            return Result.success(botCommandArgument);
        }
    }
}