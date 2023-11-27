package com.akjostudios.acsp.bot.definition.field;

import com.akjostudios.acsp.bot.definition.message.embed.field.BotMessageEmbedFieldDefinitionValidator;
import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
@RequiredArgsConstructor
public class BotFieldDefinitionValidator implements Validator<BotFieldDefinition> {
    @Qualifier("availableLocales")
    private final List<Locale> availableLocales;

    private final BotMessageEmbedFieldDefinitionValidator botMessageEmbedFieldDefinitionValidator;

    @Override
    public Result<BotFieldDefinition> validate(@NotNull BotFieldDefinition botFieldDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorRules.stringIsNotBlank(
                "Label for a field must be provided!"
        ).validate(botFieldDefinition.getLabel()));

        aggregator.add(ValidatorRules.stringIsNotBlank(
                String.format(
                        "Locale for field '%s' must be provided!",
                        botFieldDefinition.getLabel()
                )
        ).validate(botFieldDefinition.getLocale()));

        aggregator.add(ValidatorRules.collectionContains(
                botFieldDefinition.getLocale(),
                String.format(
                        "Locale '%s' for field '%s' is not available!",
                        botFieldDefinition.getLocale(),
                        botFieldDefinition.getLabel()
                )
        ).validate(availableLocales.stream().map(Locale::toString).toList()));

        aggregator.add(ValidatorRules.isNotNull(
                String.format(
                        "Field for field '%s' must be provided!",
                        botFieldDefinition.getLabel()
                )
        ).validate(botFieldDefinition.getField()));

        if (botFieldDefinition.getField() != null) {
            aggregator.add(botMessageEmbedFieldDefinitionValidator.validate(botFieldDefinition.getField()));
        }

        return aggregator.aggregateBut(botFieldDefinition);
    }
}