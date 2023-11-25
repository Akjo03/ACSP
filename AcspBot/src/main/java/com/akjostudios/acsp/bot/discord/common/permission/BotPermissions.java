package com.akjostudios.acsp.bot.discord.common.permission;

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
public class BotPermissions implements Constructable {
    @Contract(" -> new")
    public static @NotNull BotPermissionsConstructor constructor() {
        return new BotPermissionsConstructor();
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class BotPermissionsConstructor implements Constructor<BotPermissions, BotPermissionsConstructor> {
        @Override
        public @NotNull Result<BotPermissions> build() {
            return Result.success(new BotPermissions());
        }

        @Override
        public @NotNull Result<BotPermissions> validate(@NotNull BotPermissions botPermissions) {
            return Result.success(botPermissions);
        }
    }
}