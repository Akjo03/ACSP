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
public class BotCommandArguments implements Constructable {
    @Contract(" -> new")
    public static @NotNull BotCommandArgumentsConstructor constructor() {
        return new BotCommandArgumentsConstructor();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BotCommandArgumentsConstructor implements Constructor<BotCommandArguments, BotCommandArgumentsConstructor> {
        @Override
        public @NotNull Result<BotCommandArguments> build() {
            return Result.success(new BotCommandArguments());
        }

        @Override
        public @NotNull Result<BotCommandArguments> validate(@NotNull BotCommandArguments botCommandArguments) {
            return Result.success(botCommandArguments);
        }
    }
}