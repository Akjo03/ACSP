package com.akjostudios.acsp.bot.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ExceptionResponse {
    private final String message;
    private final String code;
    private final String path;
}