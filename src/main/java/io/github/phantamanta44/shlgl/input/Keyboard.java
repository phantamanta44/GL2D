package io.github.phantamanta44.shlgl.input;

import org.lwjgl.glfw.GLFW;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents the keyboard and caches key state.
 * @author Evan Geng
 */
public class Keyboard {

    /**
     * The cache of keys, mapped to their current state.
     */
    private final Map<Integer, AtomicBoolean> keyState;

    /**
     * Creates a keyboard instance.
     */
    Keyboard() {
        keyState = new HashMap<>();
    }

    /**
     * Updates the state of a key.
     * @param key The key in question.
     * @param action The action performed on the key.
     */
    void update(int key, int action) {
        switch (action) {
            case GLFW.GLFW_PRESS:
                keyState.computeIfAbsent(key, k -> new AtomicBoolean()).set(true);
                break;
            case GLFW.GLFW_RELEASE:
                keyState.computeIfAbsent(key, k -> new AtomicBoolean()).set(false);
                break;
        }
    }

    // TODO Public accessors

}
