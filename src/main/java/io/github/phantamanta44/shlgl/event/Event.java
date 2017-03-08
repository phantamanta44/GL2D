package io.github.phantamanta44.shlgl.event;

/**
 * An event postable to an {@link EventStream}.
 * @author Evan Geng
 */
public class Event {

    /**
     * Whether this event has been cancelled or not.
     */
    private boolean cancelled = false;

    /**
     * Checks if this event is cancellable.
     * @return Whether this event can be cancelled or not.
     */
    public boolean isCancellable() {
        return false;
    }

    /**
     * Checks if this event has been cancelled.
     * @return Whether this event has been cancelled or not.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancel state of this event.
     * @param cancelled Whether this event should be cancelled or not.
     */
    public void setCancelled(boolean cancelled) {
        if (!isCancellable())
            throw new UnsupportedOperationException("Cannot cancel this event!");
        this.cancelled = cancelled;
    }

}
