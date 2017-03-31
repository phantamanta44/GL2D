package io.github.phantamanta44.shlgl.input;

import io.github.phantamanta44.shlgl.util.math.Vector2I;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import org.lwjgl.glfw.GLFW;

/**
 * Represents the mouse cursor and stores position and button data.
 * @author Evan Geng
 */
public class Cursor {

    /**
     * The game window's handle.
     */
    private final long windowHandle;

    /**
     * The mouse button state bitmask. The rightmost bit is left, followed by right, then middle.
     */
    private int btnState;

    /**
     * The cursor's x coordinate.
     */
    private int posX;

    /**
     * The cursor's y coordinate.
     */
    private int posY;

    /**
     * Instantiates the cursor.
     */
    Cursor(long windowHandle) {
        this.windowHandle = windowHandle;
        this.btnState = this.posX = this.posY = 0;
    }

    /**
     * Updates the mouse button state.
     * @param button The button in question.
     * @param action The action performed on the button.
     */
    void updateButtonState(int button, int action) {
        switch (action) {
            case GLFW.GLFW_PRESS:
                btnState |= (1 << button);
                break;
            case GLFW.GLFW_RELEASE:
                btnState &= ~(1 << button);
                break;
        }
    }

    /**
     * Updates the cursor position.
     * @param posX The x coordinate.
     * @param posY The y coordinate.
     */
    void updatePos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Updates the scroll wheel state.
     * @param x The x offset scrolled.
     * @param y The y offset scrolled.
     */
    void updateScroll(int x, int y) {
        // TODO Implement
    }

    /**
     * Checks if a mouse button is currently being pressed.
     * @param button The button to check.
     * @return Whether the button is pressed or not.
     */
    public boolean isButtonPressed(int button) {
        return (btnState & (1 << button)) != 0;
    }

    /**
     * Returns the mouse's current position.
     * @return The mouse position, as a vector.
     */
    public Pooled<Vector2I> getMousePosition() {
        return Vector2I.of(posX, posY);
    }

    /**
     * Sets the mouse cursor position.
     * @param x The new x value.
     * @param y The new y value.
     */
    public void setMousePosition(int x, int y) {
        GLFW.glfwSetCursorPos(windowHandle, x, y);
    }

}
