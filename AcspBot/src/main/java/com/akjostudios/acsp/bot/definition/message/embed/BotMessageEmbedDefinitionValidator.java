package com.akjostudios.acsp.bot.definition.message.embed;

import com.akjostudios.acsp.bot.definition.message.embed.author.BotMessageEmbedAuthorDefinitionValidator;
import com.akjostudios.acsp.bot.definition.message.embed.field.BotMessageEmbedFieldDefinitionValidator;
import com.akjostudios.acsp.bot.definition.message.embed.footer.BotMessageEmbedFooterDefinitionValidator;
import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.result.ResultAggregator;
import io.github.akjo03.lib.validation.ValidationException;
import io.github.akjo03.lib.validation.Validator;
import io.github.akjo03.lib.validation.ValidatorRules;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class BotMessageEmbedDefinitionValidator implements Validator<BotMessageEmbedDefinition> {
    private final BotMessageEmbedAuthorDefinitionValidator botMessageEmbedAuthorDefinitionValidator;
    private final BotMessageEmbedFieldDefinitionValidator botMessageEmbedFieldDefinitionValidator;
    private final BotMessageEmbedFooterDefinitionValidator botMessageEmbedFooterDefinitionValidator;

    @Override
    public Result<BotMessageEmbedDefinition> validate(@NotNull BotMessageEmbedDefinition botMessageEmbedDefinition) {
        ResultAggregator aggregator = new ResultAggregator();

        boolean descriptionExists = botMessageEmbedDefinition.getDescription() != null && !botMessageEmbedDefinition.getDescription().isBlank();
        boolean fieldsExists = botMessageEmbedDefinition.getFields() != null && !botMessageEmbedDefinition.getFields().isEmpty();

        if (!descriptionExists && !fieldsExists) {
            aggregator.add(Result.fail(new ValidationException("Either a description or a field must be present for a message embed!")));
        }

        if (botMessageEmbedDefinition.getAuthor() != null) {
            aggregator.add(botMessageEmbedAuthorDefinitionValidator.validate(botMessageEmbedDefinition.getAuthor()));
        }

        if (botMessageEmbedDefinition.getTitle() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Title for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getTitle()));
        }

        if (botMessageEmbedDefinition.getDescription() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Description for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getDescription()));
        }

        if (botMessageEmbedDefinition.getUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "URL for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedDefinition.getUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Message Embed URL '%s' is not a valid URI!",
                                            botMessageEmbedDefinition.getUrl()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedDefinition.getColor() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Color for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getColor()));

            aggregator.add(
                    Result.from(() -> Long.parseLong(botMessageEmbedDefinition.getColor(), 16))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Message Embed Color '%s' is not a valid hex color!",
                                            botMessageEmbedDefinition.getColor()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedDefinition.getFields() != null) {
            botMessageEmbedDefinition.getFields().stream()
                    .map(botMessageEmbedFieldDefinitionValidator::validate)
                    .forEach(aggregator::add);
        }

        if (botMessageEmbedDefinition.getImageUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Image URL for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getImageUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedDefinition.getImageUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Message Embed Image URL '%s' is not a valid URI!",
                                            botMessageEmbedDefinition.getImageUrl()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedDefinition.getThumbnailUrl() != null) {
            aggregator.add(ValidatorRules.stringIsNotBlank(
                    "Thumbnail URL for message embed must be provided!"
            ).validate(botMessageEmbedDefinition.getThumbnailUrl()));

            aggregator.add(
                    Result.from(() -> URI.create(botMessageEmbedDefinition.getThumbnailUrl()))
                            .mapError(throwable -> new IllegalArgumentException(
                                    String.format(
                                            "Message Embed Thumbnail URL '%s' is not a valid URI!",
                                            botMessageEmbedDefinition.getThumbnailUrl()
                                    ), throwable
                            ))
            );
        }

        if (botMessageEmbedDefinition.getFooter() != null) {
            aggregator.add(botMessageEmbedFooterDefinitionValidator.validate(botMessageEmbedDefinition.getFooter()));
        }

        return aggregator.aggregateBut(botMessageEmbedDefinition);
    }
}