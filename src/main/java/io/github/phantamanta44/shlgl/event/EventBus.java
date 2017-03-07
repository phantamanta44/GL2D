package io.github.phantamanta44.shlgl.event;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles the dispatching of events to individual {@link EventStream}s.
 * @author Evan Geng
 */
public class EventBus {

    /**
     * The mapping of event types to {@link EventStream}s.
     */
    private final Map<Class<? extends Event>, EventStream> eventStreams;

    /**
     * Creates and initializes the event bus.
     */
    public EventBus() {
        eventStreams = new HashMap<>();
        // TODO Register default event types
    }

    /**
     * Registers an event type.
     * @param eventType The new event type.
     */
    public void registerEvent(Class<? extends Event> eventType) {
        eventStreams.put(eventType, new EventStream<>());
    }

    /**
     * Dispatches an event to all eligible listeners.
     * @param event The event to post.
     */
    @SuppressWarnings("unchecked")
    public void post(Event event) {
        eventStreams.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(event.getClass()))
                .forEach(e -> e.getValue().post(event));
    }

}
