package io.github.phantamanta44.shlgl.render;

import io.github.phantamanta44.shlgl.model.IHandled;
import io.github.phantamanta44.shlgl.util.math.Vector2I;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.awt.image.BufferedImage;
import java.nio.IntBuffer;

/**
 * A render in a desktop environment.
 * @author Evan Geng
 */
public class Window implements IHandled {

    /**
     * The render's handle.
     */
    private final long handle;

    /**
     * The render's x-coordinate.
     */
    private int posX;

    /**
     * The render's y-coordinate.
     */
    private int posY;

    /**
     * The render's width.
     */
    private int width;

    /**
     * The render's height.
     */
    private int height;

    /**
     * Constructs a Window instance that interacts with the given render.
     * @param handle The render's handle.
     */
    public Window(long handle) {
        this.handle = handle;
        GLFW.glfwSwapInterval(1);
        GLFW.glfwSetWindowPosCallback(handle, (wh, w, h) -> {
            width = w;
            height = h;
        });
        GLFW.glfwSetWindowSizeCallback(handle, (wh, x, y) -> {
            posX = x;
            posY = y;
        });
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            GLFW.glfwGetWindowSize(handle, x, y);
            width = x.get();
            height = y.get();
            GLFW.glfwGetWindowPos(handle, x, y);
            posX = x.get();
            posY = y.get();
        }
    }

    @Override
    public long getHandle() {
        return handle;
    }

    /**
     * Sets the visibility state of the render.
     * @param visible Whether the render should be shown or not.
     */
    public void setVisible(boolean visible) {
        if (visible)
            GLFW.glfwShowWindow(handle);
        else
            GLFW.glfwHideWindow(handle);
    }

    /**
     * Retrieves the render's visibility state.
     * @return Whether the render is visible or not.
     */
    public boolean getVisible() {
        return GLFW.glfwGetWindowAttrib(handle, GLFW.GLFW_VISIBLE) == 1;
    }

    /**
     * Sets the render's position on the screen.
     * @param x The render's x-coordinate.
     * @param y The render's y-coordinate.
     */
    public void setPosition(int x, int y) {
        GLFW.glfwSetWindowPos(handle, x, y);
    }

    /**
     * Retrieves the render's position on the screen.
     * @return A pooled vector of the x and y coordinates.
     */
    public Pooled<Vector2I> getPosition() {
        return Vector2I.of(posX, posY);
    }

    /**
     * Retrieves the render's x-coordinate.
     * @return The x-coordinate.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Retrieves the render's y-coordinate.
     * @return The y-coordinate.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Sets the render's size.
     * @param width The render's width.
     * @param height The render's height.
     */
    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(handle, width, height);
    }

    /**
     * Retrieves the render's size.
     * @return A pooled vector of the width and height.
     */
    public Pooled<Vector2I> getSize() {
        return Vector2I.of(width, height);
    }

    /**
     * Retrieves the render's width.
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the render's height.
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Instructs the OS to give the render focus.
     */
    public void focus() {
        GLFW.glfwFocusWindow(handle);
    }

    /**
     * Sets the render's resizability attribute.
     * @param resizable Whether the render is user-resizable or not.
     */
    public void setResizable(boolean resizable) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_RESIZABLE, resizable ? 1 : 0);
    }

    /**
     * Sets the render's always-on-top attribute.
     * @param alwaysOnTop Whether the render should always be on top or not.
     */
    public void setAlwaysOnTop(boolean alwaysOnTop) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_FLOATING, alwaysOnTop ? 1 : 0);
    }

    /**
     * Sets the render's decoration (i.e. borders) state.
     * @param decorated Whether to decorate the render or not.
     */
    public void setDecorated(boolean decorated) {
        GLFW.glfwSetWindowAttrib(handle, GLFW.GLFW_DECORATED, decorated ? 1 : 0);
    }

    /**
     * Sets the render's titlebar text.
     * @param title The title text.
     */
    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(handle, title);
    }

    /**
     * Sets the render's icon. <b>NO IMPLEMENTATION!</b>
     * @param icon The new render icon.
     */
    public void setIcon(BufferedImage icon) {
        throw new NotImplementedException(); // TODO Implement
    }

}
