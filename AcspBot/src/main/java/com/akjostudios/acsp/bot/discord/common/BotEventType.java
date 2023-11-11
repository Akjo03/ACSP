package com.akjostudios.acsp.bot.discord.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.events.ExceptionEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
@SuppressWarnings("unused")
public enum BotEventType {
    EXCEPTION(-1, ExceptionEvent.class),
    STATUS_CHANGE(0, StatusChangeEvent.class);

    private final int id;
    private final Class<? extends GenericEvent> eventClass;

    private static final Map<Integer, BotEventType> ID_MAP = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(BotEventType::getId, type -> type));

    private static final Map<Class<? extends GenericEvent>, BotEventType> CLASS_MAP = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(BotEventType::getEventClass, type -> type));

    public static Optional<BotEventType> getEventType(int id) {
        return Optional.ofNullable(ID_MAP.get(id));
    }

    public static Optional<BotEventType> getEventType(Class<? extends GenericEvent> eventClass) {
        return Optional.ofNullable(CLASS_MAP.get(eventClass));
    }
}