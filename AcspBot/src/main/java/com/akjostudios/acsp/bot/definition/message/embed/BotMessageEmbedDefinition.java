package com.akjostudios.acsp.bot.definition.message.embed;

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
    String color;
    List<BotMessageEmbedFieldDefinition> fields;
    String imageUrl;
    String thumbnailUrl;
    BotMessageEmbedFooterDefinition footer;
}