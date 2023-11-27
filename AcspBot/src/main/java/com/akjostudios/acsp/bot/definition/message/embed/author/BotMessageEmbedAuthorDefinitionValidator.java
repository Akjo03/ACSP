package com.akjostudios.acsp.bot.definition.message.embed.author;

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
public class BotMessageEmbedAuthorDefinitionValidator implements Validator<BotMessageEmbedAuthorDefinition> {
    @Override
    public Result<BotMessageEmbedAuthorDefinition> validate(@NotNull BotMessageEmbedAuthorDefinition botMessageEmbedAuthorDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        aggregator.add(ValidatorRules.stringIsNotBlank(
                "Author name for an embed must be provided!"
        ).validate(botMessageEmbedAuthorDefinition.getName()));

        if (botMessageEmbedAuthorDefinition.getUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Author URL for an embed must be provided!"
            ).validate(botMessageEmbedAuthorDefinition.getUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedAuthorDefinition.getUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Author URL '%s' for an embed is not a valid URI!",
                                            botMessageEmbedAuthorDefinition.getUrl()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedAuthorDefinition.getIconUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Author icon URL for an embed must be provided!"
            ).validate(botMessageEmbedAuthorDefinition.getIconUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedAuthorDefinition.getIconUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Author icon URL '%s' for an embed is not a valid URI!",
                                            botMessageEmbedAuthorDefinition.getIconUrl()
                                    ), throwable
                            ))
            );
        }

        return aggregator.aggregateBut(botMessageEmbedAuthorDefinition);
    }
}