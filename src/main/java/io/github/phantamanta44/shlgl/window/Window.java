package io.github.phantamanta44.shlgl.window;

import io.github.phantamanta44.shlgl.model.IHandled;
import io.github.phantamanta44.shlgl.util.math.Vector2I;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

/**
 * A window in a desktop environment.
 * @author Evan Geng
 */
public class Window implements IHandled {

    /**
     * The window's handle.
     */
    private final long handle;

    /**
     * Constructs a Window instance that interacts with the given window.
     * @param handle The window's handle.
     */
    public Window(long handle) {
        this.handle = handle;
        GLFW.glfwSwapInterval(1);
    }

    @Override
    public long getHandle() {
        return handle;
    }

    /**
     * Sets the visibility state of the window.
     * @param visible Whether the window should be shown or not.
     */
    public void setVisible(boolean visible) {
        if (visible)
            GLFW.glfwShowWindow(handle);
        else
            GLFW.glfwHideWindow(handle);
    }

    /**
     * Retrieves the window's visibility state.
     * @return Whether the window is visible or not.
     */
    public boolean getVisible() {
        return GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_VISIBLE) == 1;
    }

    /**
     * Sets the window's position on the screen.
     * @param x The window's x-coordinate.
     * @param y The window's y-coordinate.
     */
    public void setPosition(int x, int y) {
        GLFW.glfwSetWindowPos(handle, x, y);
    }

    /**
     * Retrieves the window's position on the screen.
     * @return A vector of the x and y coordinates.
     */
    public Vector2I getPosition() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            GLFW.glfwGetWindowPos(handle, x, y);
            return Vector2I.of(x.get(), y.get());
        }
    }

    /**
     * Sets the window's size.
     * @param width The window's width.
     * @param height The window's height.
     */
    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(handle, width, height);
    }

    /**
     * Retrieves the window's size.
     * @return A vector of the width and height.
     */
    public Vector2I getSize() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(handle, x, y);
            return Vector2I.of(x.get(), y.get());
        }
    }

    /**
     * Instructs the OS to give the window focus.
     */
    public void focus() {
        GLFW.glfwFocusWindow(handle);
    }

    /**
     * Sets the window's resizability attribute.
     * @param resizable Whether the window is user-resizable or not.
     */
    public void setResizable(boolean resizable) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_RESIZABLE, resizable ? 1 : 0);
    }

    /**
     * Sets the window's always-on-top attribute.
     * @param alwaysOnTop Whether the window should always be on top or not.
     */
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_FLOATING, alwaysOnTop ? 1 : 0);
    }

    /**
     * Sets the window's decoration (i.e. borders) state.
     * @param decorated Whether to decorate the window or not.
     */
    public void setDecorated(boolean decorated) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_DECORATED, decorated ? 1 : 0);
    }

    /**
     * Sets the window's titlebar text.
     * @param title The title text.
     */
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(handle, title);
    }

    /**
     * Sets the window's icon. <b>NO IMPLEMENTATION!</b>
     * @param icon The new window icon.
     */
    public void setIcon(BufferedImage icon) {
        throw new NotImplementedException(); // TODO Implement
    }

}
