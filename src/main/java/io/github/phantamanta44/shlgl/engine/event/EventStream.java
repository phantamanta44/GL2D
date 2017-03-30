package io.github.phantamanta44.shlgl.engine.event;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A stream of events. Listeners can be registered to handle events when they're posted.
 * @author Evan Geng
 */
public class EventStream<T extends Event> {

    /**
     * The set of all permanent listeners.
     */
    private final Map<ListenerPriority, List<IListener<T>>> perm;

    /**
     * The set of all temporary listeners.
     */
    private final Map<ListenerPriority, List<IListener<T>>> temp;

    /**
     * Constructs an event stream.
     */
    public EventStream() {
        perm = new HashMap<>();
        temp = new HashMap<>();
        for (ListenerPriority priority : ListenerPriority.values()) {
            perm.put(priority, new LinkedList<>());
            temp.put(priority, new LinkedList<>());
        }
    }

    /**
     * Dispatches an event to all listeners on this stream.
     * @param event The event to post.
     */
    void post(T event) {
        for (ListenerPriority priority : ListenerPriority.values()) {
            perm.get(priority).forEach(l -> l.onEvent(event));
            temp.get(priority).forEach(l -> l.onEvent(event));
        }
        temp.forEach((p, l) -> l.clear());
    }

    /**
     * Registers an event listener on this stream.
     * @param listener The listener.
     */
    public void listen(IListener<T> listener) {
        perm.get(listener.getPriority()).add(listener);
    }

    /**
     * Registers a one-time listener on this stream. After receiving one event, the listener is removed.
     * @param listener The listener.
     */
    public void once(IListener<T> listener) {
        temp.get(listener.getPriority()).add(listener);
    }
    
}
