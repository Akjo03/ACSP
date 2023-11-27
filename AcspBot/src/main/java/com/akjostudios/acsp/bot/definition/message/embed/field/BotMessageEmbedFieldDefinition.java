package com.akjostudios.acsp.bot.definition.message.embed.field;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageEmbedFieldDefinition {
    String name;
    String value;
    boolean inline;
}