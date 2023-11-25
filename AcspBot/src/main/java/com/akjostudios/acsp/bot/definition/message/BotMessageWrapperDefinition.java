package com.akjostudios.acsp.bot.definition.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageWrapperDefinition {
    String label;
    String locale;
    BotMessageDefinition message;
}