package com.akjostudios.acsp.bot.definition;

import com.akjostudios.acsp.bot.definition.field.BotFieldDefinitionValidator;
import com.akjostudios.acsp.bot.definition.message.wrapper.BotMessageWrapperDefinitionValidator;
import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorGroup;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotDefinitionValidator implements Validator<BotDefinition> {
    private final BotMessageWrapperDefinitionValidator botMessageWrapperDefinitionValidator;
    private final BotFieldDefinitionValidator botFieldDefinitionValidator;

    @Override
    public Result<BotDefinition> validate(@NotNull BotDefinition botDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorGroup.of(
                ValidatorRules.stringIsNotBlank(
                        "Command prefix for bot definition must be provided!"
                ), ValidatorRules.stringDoesNotStartWith(
                        "/",
                        "Command prefix for bot definition must not start with '/'!"
                )
        ).validate(botDefinition.getCommandPrefix()));

        botDefinition.getMessages().stream()
                .map(botMessageWrapperDefinitionValidator::validate)
                .forEach(aggregator::add);

        botDefinition.getFields().stream()
                .map(botFieldDefinitionValidator::validate)
                .forEach(aggregator::add);

        return aggregator.aggregateBut(botDefinition, "Bot Definition Validation Report");
    }
}