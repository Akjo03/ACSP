package com.akjostudios.acsp.bot.definition.message;

import com.akjostudios.acsp.bot.definition.message.embed.BotMessageEmbedDefinitionValidator;
import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.ValidationException;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotMessageDefinitionValidator implements Validator<BotMessageDefinition> {
    private final BotMessageEmbedDefinitionValidator botMessageEmbedDefinitionValidator;

    @Override
    public Result<BotMessageDefinition> validate(@NotNull BotMessageDefinition botMessageDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        boolean contentExists = botMessageDefinition.getContent() != null && !botMessageDefinition.getContent().isBlank();
        boolean embedsExists = botMessageDefinition.getEmbeds() != null && !botMessageDefinition.getEmbeds().isEmpty();

        if (!contentExists && !embedsExists) {
            aggregator.add(Result.fail(new ValidationException("Message must have content or at least one embed!")));
        }

        if (botMessageDefinition.getContent() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Message content must be provided!"
            ).validate(botMessageDefinition.getContent()));
        }

        if (botMessageDefinition.getEmbeds() != null) {
            botMessageDefinition.getEmbeds().stream()
                    .map(botMessageEmbedDefinitionValidator::validate)
                    .forEach(aggregator::add);
        }

        return aggregator.aggregateBut(botMessageDefinition);
    }
}