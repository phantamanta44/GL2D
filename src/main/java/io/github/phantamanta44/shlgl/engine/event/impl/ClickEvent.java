package io.github.phantamanta44.shlgl.engine.event.impl;

import io.github.phantamanta44.shlgl.engine.event.Event;

/**
 * Posted upon an interaction with a mouse button.
 * @author Evan Geng
 */
public class ClickEvent extends Event {

    /**
     * The mouse button referenced by this event.
     */
    private final int button;

    /**
     * The action performed upon the key.
     */
    private final int action;

    /**
     * Constructs a click event.
     * @param button The mouse button in question.
     * @param action The action performed upon the button.
     */
    public ClickEvent(int button, int action) {
        this.button = button;
        this.action = action;
    }

    /**
     * Retrieves the mouse button pressed.
     * @return The mouse button.
     */
    public int getButton() {
        return button;
    }

    /**
     * Retrieves the action performed upon the button.
     * @return The action.
     */
    public int getAction() {
        return action;
    }

}
