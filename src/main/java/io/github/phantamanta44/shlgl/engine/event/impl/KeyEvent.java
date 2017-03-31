package io.github.phantamanta44.shlgl.engine.event.impl;

import io.github.phantamanta44.shlgl.engine.event.Event;

/**
 * Posted when a key is pressed or released.
 * @author Evan Geng
 */
public class KeyEvent extends Event {

    /**
     * The key referenced by this event.
     */
    private final int keyCode;

    /**
     * The action performed upon the key.
     */
    private final int action;

    /**
     * Constructs a key event.
     * @param keyCode The key in question.
     * @param action The action performed upon the key.
     */
    public KeyEvent(int keyCode, int action) {
        this.keyCode = keyCode;
        this.action = action;
    }

    /**
     * Retrieves the key code of the key.
     * @return The key code.
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Retrieves the action performed upon the key.
     * @return The action.
     */
    public int getAction() {
        return action;
    }

}
