package com.akjostudios.acsp.bot.definition.message.embed.footer;

import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BotMessageEmbedFooterDefinitionValidator implements Validator<BotMessageEmbedFooterDefinition> {
    @Override
    public Result<BotMessageEmbedFooterDefinition> validate(@NotNull BotMessageEmbedFooterDefinition botMessageEmbedFooterDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorRules.stringIsNotBlank(
                "Footer text for an embed must be provided!"
        ).validate(botMessageEmbedFooterDefinition.getText()));

        if (botMessageEmbedFooterDefinition.getTimestamp() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Footer timestamp for an embed must be provided!"
            ).validate(botMessageEmbedFooterDefinition.getTimestamp()));

            aggregator.add(
                    Result.from(() -> Long.parseLong(botMessageEmbedFooterDefinition.getTimestamp()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Footer timestamp '%s' for an embed is not a valid long!",
                                            botMessageEmbedFooterDefinition.getTimestamp()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedFooterDefinition.getIconUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Footer icon URL for an embed must be provided!"
            ).validate(botMessageEmbedFooterDefinition.getIconUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedFooterDefinition.getIconUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Footer icon URL '%s' for an embed is not a valid URI!",
                                            botMessageEmbedFooterDefinition.getIconUrl()
                                    ), throwable
                            ))
            );
        }

        return aggregator.aggregateBut(botMessageEmbedFooterDefinition);
    }
}