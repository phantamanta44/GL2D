package io.github.phantamanta44.shlgl.input;

import io.github.phantamanta44.shlgl.SHLGL;
import io.github.phantamanta44.shlgl.engine.event.impl.ClickEvent;
import io.github.phantamanta44.shlgl.engine.event.impl.KeyEvent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

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
                shlgl.getEventBus().post(new KeyEvent(key, action));
            }
        });
        GLFW.glfwSetMouseButtonCallback(window, new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                cursor.updateButtonState(button, action);
                shlgl.getEventBus().post(new ClickEvent(button, action));
            }
        });
        GLFW.glfwSetCursorPosCallback(window, new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double posX, double posY) {
                cursor.updatePos((int)posX, (int)posY);
            }
        });
    }

    /**
     * Gets the keyboard instance.
     * @return The keyboard.
     */
    public Keyboard getKeyboard() {
        return keyboard;
    }

    /**
     * Gets the cursor instance.
     * @return The cursor.
     */
    public Cursor getCursor() {
        return cursor;
    }

}
