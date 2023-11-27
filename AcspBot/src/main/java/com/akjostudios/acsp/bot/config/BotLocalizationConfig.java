package com.akjostudios.acsp.bot.config;

import com.akjostudios.acsp.bot.properties.BotLocalizationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;
import org.springframework.web.server.i18n.LocaleContextResolver;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class BotLocalizationConfig {
    private final BotLocalizationProperties localizationProperties;

    @Bean
    @Primary
    public LocaleContextResolver localeResolver() {
        AcceptHeaderLocaleContextResolver localeResolver = new AcceptHeaderLocaleContextResolver();
        localeResolver.setDefaultLocale(Locale.of(localizationProperties.getDefaultLocale()));
        return localeResolver;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(localizationProperties.getResourceName());
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }

    @Bean("availableLocales")
    public List<Locale> availableLocales() {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            return Arrays.stream(resolver.getResources("classpath*:/" + localizationProperties.getResourceName() + "*.properties"))
                    .map(resource -> Objects.requireNonNull(
                            resource.getFilename()
                    ).substring(
                            localizationProperties.getResourceName().length() + 1,
                            resource.getFilename().length() - ".properties".length()
                    )).map(Locale::forLanguageTag).toList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to load available locales!", e);
        }
    }
}