package io.github.phantamanta44.shlgl;

import io.github.phantamanta44.shlgl.window.Window;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.MemoryUtil;

/**
 * The main API entry point for SHLGL.
 * @author Evan Geng
 */
public class SHLGL {

    /**
     * The singleton instance of SHLGL.
     */
    private static SHLGL INSTANCE;

    /**
     * Retrieves the singleton instance of SHLGL.
     * @return The instance.
     */
    public static SHLGL getInstance() {
        if (INSTANCE == null)
            INSTANCE = new SHLGL();
        return INSTANCE;
    }

    /**
     * Initializes GLFW.
     */
    private SHLGL() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Could not initialize GLFW!");
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
    }

    /**
     * Creates a window with the given parameters.
     * @param width The window's width.
     * @param height The window's height.
     * @param title The window's title.
     * @return The newly-created window.
     */
    public Window createWindow(int width, int height, String title) {
        long handle = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (handle == MemoryUtil.NULL)
            throw new IllegalStateException("Window creation failed!");
        return new Window(handle);
    }

}
