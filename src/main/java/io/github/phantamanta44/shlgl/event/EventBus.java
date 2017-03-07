package io.github.phantamanta44.shlgl.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the dispatching of events to individual {@link EventStream}s.
 * @author Evan Geng
 */
public class EventBus { // TODO Document

    private final Map<Class<? extends Event>, EventStream<?>> eventStreams;

    public EventBus() {
        eventStreams = new HashMap<>();
        // TODO Register default event types
    }

    public void registerEvent(Class<? extends Event> eventType) {
        eventStreams.put(eventType, new EventStream<>());
    }

    public void post(Event event) {
        eventStreams.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(event.getClass()))
                .forEach(e -> e.getValue().post(event)); // FIXME Cast this somehow
    }

}
