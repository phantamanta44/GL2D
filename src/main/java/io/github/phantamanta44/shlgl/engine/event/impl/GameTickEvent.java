package io.github.phantamanta44.shlgl.engine.event.impl;

import io.github.phantamanta44.shlgl.engine.event.Event;

/**
 * Posted every game tick. Game logic should be handled in listeners.
 * @author Evan Geng
 */
public class GameTickEvent extends Event {

    /**
     * The tick number.
     */
    private final long tick;

    /**
     * Creates a game tick event for the given tick number.
     * @param tick The tick number.
     */
    public GameTickEvent(long tick) {
        this.tick = tick;
    }

    /**
     * Retrieves the tick counter value for this event.
     * @return The tick number.
     */
    public long getTick() {
        return tick;
    }

}
