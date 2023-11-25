package com.akjostudios.acsp.bot.definition.message;

import com.akjostudios.acsp.bot.definition.message.embed.BotMessageEmbedDefinition;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageDefinition {
    String content;
    List<BotMessageEmbedDefinition> embeds;
}