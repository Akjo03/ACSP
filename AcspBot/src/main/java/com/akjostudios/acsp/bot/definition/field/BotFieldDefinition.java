package com.akjostudios.acsp.bot.definition.field;

import com.akjostudios.acsp.bot.definition.message.embed.BotMessageEmbedFieldDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotFieldDefinition {
    String label;
    String locale;
    BotMessageEmbedFieldDefinition field;
}