package io.github.phantamanta44.shlgl.engine.event;

import io.github.phantamanta44.shlgl.engine.event.impl.GameTickEvent;
import io.github.phantamanta44.shlgl.engine.event.impl.RenderEvent;

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
        registerEvent(GameTickEvent.class);
        registerEvent(RenderEvent.class);
    }

    /**
     * Registers an event type.
     * @param eventType The new event type.
     */
    public void registerEvent(Class<? extends Event> eventType) {
        eventStreams.put(eventType, new EventStream<>());
    }

    /**
     * Registers a listener for the given event.
     * @param eventType The event to listen for.
     * @param listener The event listener.
     * @param <T> The type of the event to listen for.
     */
    public <T extends Event> void on(Class<T> eventType, IListener<T> listener) {
        streamFor(eventType).listen(listener);
    }

    /**
     * Registers a one-time listener for the given event.
     * @param eventType The event to listen for.
     * @param listener The event listener.
     * @param <T> The type of the event to listen for.
     */
    public <T extends Event> void once(Class<T> eventType, IListener<T> listener) {
        streamFor(eventType).once(listener);
    }

    /**
     * Retrieves the event stream for a given event.
     * @param eventType The event to look up.
     * @param <T> The type of the event.
     * @return The event stream.
     */
    @SuppressWarnings("unchecked")
    private <T extends Event> EventStream<T> streamFor(Class<T> eventType) {
        EventStream<T> stream = eventStreams.get(eventType);
        if (stream == null)
            throw new IllegalArgumentException("Event type not registered: " + eventType.getName());
        return stream;
    }

    /**
     * Dispatches an event to all eligible listeners.
     * @param event The event to post.
     * @return <code>false</code> if the event was cancelled; otherwise, <code>true</code>.
     */
    @SuppressWarnings("unchecked")
    public boolean post(Event event) {
        eventStreams.entrySet().stream()
                .filter(e -> e.getKey().isAssignableFrom(event.getClass()))
                .forEach(e -> e.getValue().post(event));
        return !event.isCancelled();
    }

}
