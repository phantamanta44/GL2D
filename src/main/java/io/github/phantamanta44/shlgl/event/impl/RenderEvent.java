package io.github.phantamanta44.shlgl.event.impl;

import io.github.phantamanta44.shlgl.event.Event;
import io.github.phantamanta44.shlgl.render.RenderBuffer;

/**
 * Posted every render tick. Game renders should be handled in listeners.
 * @author Evan Geng
 */
public class RenderEvent extends Event {

    /**
     * The graphics buffer.
     */
    private final RenderBuffer buffer;

    /**
     * Creates a render event with the given graphics buffer.
     * @param buffer The graphics buffer.
     */
    public RenderEvent(RenderBuffer buffer) {
        this.buffer = buffer;
    }

    /**
     * Retrieves the graphics buffer for this event.
     * @return The graphics buffer.
     */
    public RenderBuffer getBuffer() {
        return buffer;
    }

}
