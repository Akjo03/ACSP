package com.akjostudios.acsp.bot.definition.message.embed;

import com.akjostudios.acsp.bot.definition.message.embed.author.BotMessageEmbedAuthorDefinition;
import com.akjostudios.acsp.bot.definition.message.embed.field.BotMessageEmbedFieldDefinition;
import com.akjostudios.acsp.bot.definition.message.embed.footer.BotMessageEmbedFooterDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageEmbedDefinition {
    BotMessageEmbedAuthorDefinition author;
    String title;
    String description;
    String url;
    String color; // Must be converted from hex to decimal
    List<BotMessageEmbedFieldDefinition> fields;
    String imageUrl;
    String thumbnailUrl;
    BotMessageEmbedFooterDefinition footer;
}