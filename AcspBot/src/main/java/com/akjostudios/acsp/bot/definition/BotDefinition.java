package com.akjostudios.acsp.bot.definition;

import com.akjostudios.acsp.bot.definition.field.BotFieldDefinition;
import com.akjostudios.acsp.bot.definition.message.BotMessageWrapperDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotDefinition {
    String commandPrefix;
    List<BotMessageWrapperDefinition> messages;
    List<BotFieldDefinition> fields;
}