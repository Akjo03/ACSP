package com.akjostudios.acsp.bot.definition;

import io.github.akjo03.lib.result.Result;
import io.github.akjo03.lib.validation.Validator;
import org.springframework.stereotype.Component;

@Component
public class BotDefinitionValidator implements Validator<BotDefinition> {
    @Override
    public Result<BotDefinition> validate(BotDefinition botDefinition) {
        // TODO: Validate bot definition
        return Result.success(botDefinition);
    }
}