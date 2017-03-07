package io.github.phantamanta44.shlgl.event;

/**
 * An event listener.
 * @author Evan Geng
 */
@FunctionalInterface
public interface IListener<T extends Event> {

    /**
     * Handles the posted event.
     * @param event The event.
     */
    void onEvent(T event);

    /**
     * Retrieves this listener's priority.
     * @return The priority.
     */
    default ListenerPriority getPriority() {
        return ListenerPriority.NORMAL;
    }

}
