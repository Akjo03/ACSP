package com.akjostudios.acsp.bot.services.bot;

import com.akjostudios.acsp.bot.config.LocaleConfiguration;
import com.akjostudios.acsp.bot.constants.BotLanguages;
import com.akjostudios.acsp.bot.services.StringPlaceholderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BotStringsService {
	private final StringPlaceholderService stringPlaceholderService;
	private final ResourceBundleMessageSource resourceBundleMessageSource;
	private final LocaleConfiguration localeConfiguration;

	public String getString(String label, Optional<BotLanguages> language, String... placeholders) {
		return stringPlaceholderService.replacePlaceholders(
				resourceBundleMessageSource.getMessage(label, null, localeConfiguration.getLanguage(language).getLocale()),
				placeholders
		);
	}
}