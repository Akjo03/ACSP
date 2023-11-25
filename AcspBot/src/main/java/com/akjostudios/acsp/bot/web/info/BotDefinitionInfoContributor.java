package com.akjostudios.acsp.bot.web.info;

import com.akjostudios.acsp.bot.definition.BotDefinition;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BotDefinitionInfoContributor implements InfoContributor {
    private final BotDefinition botDefinition;

    @Override
    public void contribute(Info.@NotNull Builder builder) {
        builder.withDetail("botDefinition", botDefinition);
    }
}