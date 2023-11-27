package com.akjostudios.acsp.bot.definition.message.embed.footer;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BotMessageEmbedFooterDefinition {
    String text;
    String timestamp;
    String iconUrl;
}