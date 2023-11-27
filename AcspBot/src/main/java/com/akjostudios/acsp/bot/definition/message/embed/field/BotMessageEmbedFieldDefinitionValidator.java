package com.akjostudios.acsp.bot.definition.message.embed.field;

import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotMessageEmbedFieldDefinitionValidator implements Validator<BotMessageEmbedFieldDefinition> {
    @Override
    public Result<BotMessageEmbedFieldDefinition> validate(@NotNull BotMessageEmbedFieldDefinition botMessageEmbedFieldDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorRules.stringIsNotBlank(
                "Name for a field must be provided!"
        ).validate(botMessageEmbedFieldDefinition.getName()));

        aggregator.add(ValidatorRules.stringIsNotBlank(
                String.format(
                        "Value for field '%s' must be provided!",
                        botMessageEmbedFieldDefinition.getName()
                )
        ).validate(botMessageEmbedFieldDefinition.getValue()));

        return aggregator.aggregateBut(botMessageEmbedFieldDefinition);
    }
}