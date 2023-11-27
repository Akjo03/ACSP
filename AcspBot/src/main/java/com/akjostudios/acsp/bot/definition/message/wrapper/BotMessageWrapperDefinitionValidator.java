package com.akjostudios.acsp.bot.definition.message.wrapper;

import com.akjostudios.acsp.bot.definition.message.BotMessageDefinitionValidator;
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
public class BotMessageWrapperDefinitionValidator implements Validator<BotMessageWrapperDefinition> {
    @Qualifier("availableLocales")
    private final List<Locale> availableLocales;

    private final BotMessageDefinitionValidator botMessageDefinitionValidator;

    @Override
    public Result<BotMessageWrapperDefinition> validate(@NotNull BotMessageWrapperDefinition botMessageWrapperDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorRules.stringIsNotBlank(
                "Label for a message definition must be provided!"
        ).validate(botMessageWrapperDefinition.getLabel()));

        aggregator.add(ValidatorRules.stringIsNotBlank(
                String.format(
                        "Message locale for message definition '%s' must be provided!",
                        botMessageWrapperDefinition.getLabel()
                )
        ).validate(botMessageWrapperDefinition.getLocale()));

        aggregator.add(ValidatorRules.collectionContains(
                botMessageWrapperDefinition.getLocale(),
                String.format(
                        "Locale '%s' for message definition '%s' is not available!",
                        botMessageWrapperDefinition.getLocale(),
                        botMessageWrapperDefinition.getLabel()
                )
        ).validate(availableLocales.stream().map(Locale::toString).toList()));

        aggregator.add(ValidatorRules.isNotNull(
                String.format(
                        "Message for message definition '%s' must be provided!",
                        botMessageWrapperDefinition.getLabel()
                )
        ).validate(botMessageWrapperDefinition.getMessage()));

        if (botMessageWrapperDefinition.getMessage() != null) {
            aggregator.add(botMessageDefinitionValidator.validate(botMessageWrapperDefinition.getMessage()));
        }

        return aggregator.aggregateBut(botMessageWrapperDefinition);
    }
}