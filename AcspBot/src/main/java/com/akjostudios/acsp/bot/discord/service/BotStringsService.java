package com.akjostudios.acsp.bot.discord.service;

import com.akjostudios.acsp.bot.properties.BotLocalizationProperties;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BotStringsService {
    private final ResourceBundleMessageSource messageSource;
    private final BotLocalizationProperties localizationProperties;

    public @NotNull String getString(@NotNull String label, String@NotNull... placeholders) {
        return getString(label, Locale.of(localizationProperties.getDefaultLocale()), placeholders);
    }

    public @NotNull String getString(@NotNull String label, @NotNull Locale locale, String@NotNull... placeholders) {
        return replacePlaceholders(messageSource.getMessage(label, null, locale), placeholders);
    }

    public @NotNull String replacePlaceholders(@NotNull String message, String@NotNull... placeholders) {
        return IntStream.range(0, placeholders.length)
                .mapToObj(i -> message.replace("$" + i, placeholders[i]))
                .reduce((first, second) -> second)
                .orElse(message);
    }
}