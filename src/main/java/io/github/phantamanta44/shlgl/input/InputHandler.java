package io.github.phantamanta44.shlgl.input;

import io.github.phantamanta44.shlgl.SHLGL;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

/**
 * Handles input and dispatches relevant events.
 * @author Evan Geng
 */
public class InputHandler {

    /**
     * The SHLGL instance.
     */
    private final SHLGL shlgl;

    /**
     * The keyboard.
     */
    private final Keyboard keyboard;

    /**
     * The mouse cursor.
     */
    private final Cursor cursor;

    /**
     * Instantiates the input handler and registers input callbacks.
     * @param shlgl The SHLGL instance.
     */
    public InputHandler(SHLGL shlgl) {
        this.shlgl = shlgl;
        this.keyboard = new Keyboard();
        this.cursor = new Cursor();
        long window = shlgl.getGameWindow().getHandle();
        GLFW.glfwSetKeyCallback(window, new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keyboard.update(key, action);
                // TODO Emit keyboard event
            }
        });
        // TODO Mouse callbacks
    }

}
