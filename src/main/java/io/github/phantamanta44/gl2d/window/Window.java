package io.github.phantamanta44.gl2d.window;

import io.github.phantamanta44.gl2d.model.IHandled;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;

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
        GLFW.glfwShowWindow(handle);
    }

    @Override
    public long getHandle() {
        return handle;
    }

    /**
     * Destroys this window.
     */
    public void destroy() {
        Callbacks.glfwFreeCallbacks(handle);
        GLFW.glfwDestroyWindow(handle);
    }

}
