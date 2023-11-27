package com.akjostudios.acsp.bot.definition.message.embed.author;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageEmbedAuthorDefinition {
    String name;
    String url;
    String iconUrl;
}