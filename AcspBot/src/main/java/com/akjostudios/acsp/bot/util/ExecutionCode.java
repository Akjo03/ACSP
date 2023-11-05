package com.akjostudios.acsp.bot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@SuppressWarnings("unused")
public enum ExecutionCode {
    RESTART(-1),
    SUCCESS(0),
    GENERIC_ERROR(1);

    private final int code;
}