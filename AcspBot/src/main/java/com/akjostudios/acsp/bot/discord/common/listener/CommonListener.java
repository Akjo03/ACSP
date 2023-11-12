package com.akjostudios.acsp.bot.discord.common.listener;

import com.akjostudios.acsp.bot.discord.common.BotEventType;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CommonListener implements EventListener {
    private final Map<Class<? extends GenericEvent>, List<BotListener<?>>> listeners;

    @Autowired
    @SuppressWarnings("unchecked")
    public CommonListener(@NotNull List<BotListener<?>> botListeners) {
        this.listeners = botListeners.stream()
                .collect(Collectors.groupingBy(botListener ->
                        (Class<? extends GenericEvent>) ((ParameterizedType) botListener.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]
                ));
        listeners.forEach((key, value) ->
                log.info("Found {} listeners for event type {}", value.size(), BotEventType.getByClass(key).orElse(BotEventType.OTHER))
        );
    }

    @Override
    public void onEvent(@NotNull GenericEvent event) {
        List<BotListener<?>> eventListeners = listeners.get(event.getClass());
        BotEventType eventType = BotEventType.getByClass(event.getClass()).orElse(BotEventType.OTHER);

        if (eventListeners == null) { return; }

        if (eventType == BotEventType.OTHER) {
            log.warn("Event {} is not a known event type!", event.getClass().getSimpleName());
            listeners.get(GenericEvent.class).forEach(listener -> invokeListener(listener, event, eventType));
            return;
        }

        eventListeners.forEach(listener -> invokeListener(listener, event, eventType));
    }

    @SuppressWarnings("unchecked")
    private <T extends GenericEvent> void invokeListener(@NotNull BotListener<T> listener, @NotNull GenericEvent event, @NotNull BotEventType eventType) {
        log.info("Invoking listener {} for event {}", listener.getClass().getSimpleName(), eventType);
        try { listener.onEvent((T) event); } catch (Exception e) {
            log.error("Error invoking listener {} for event {}", listener.getClass().getSimpleName(), event.getClass().getSimpleName(), e);
        }
    }
}