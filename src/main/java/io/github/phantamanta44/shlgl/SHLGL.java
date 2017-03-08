package io.github.phantamanta44.shlgl;

import io.github.phantamanta44.shlgl.event.EventBus;
import io.github.phantamanta44.shlgl.event.impl.RenderEvent;
import io.github.phantamanta44.shlgl.event.impl.GameTickEvent;
import io.github.phantamanta44.shlgl.game.TickTimer;
import io.github.phantamanta44.shlgl.window.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;

import static org.lwjgl.system.MemoryUtil.NULL;

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
     * Initializes GLFW and constructs the game window.
     * @param windowWidth The game window's initial width.
     * @param windowHeight The game window's initial height.
     * @param windowTitle The game window's initial title bar text.
     */
    public static void init(int windowWidth, int windowHeight, String windowTitle) {
        if (INSTANCE == null)
            INSTANCE = new SHLGL(windowWidth, windowHeight, windowTitle);
    }

    /**
     * Retrieves the singleton instance of SHLGL.
     * @return The singleton instance.
     */
    public static SHLGL getInstance() {
        return INSTANCE;
    }

    /**
     * The game window instance.
     */
    private final Window gameWindow;

    /**
     * The game event bus.
     */
    private final EventBus eventBus;

    /**
     * The game tick timer.
     */
    private final TickTimer timer;

    /**
     * The game's running state.
     */
    private boolean running;

    /**
     * The exit code to terminate with.
     */
    private int exitCode = 1;

    /**
     * Initializes GLFW and constructs the game window.
     * @param windowWidth The game window's initial width.
     * @param windowHeight The game window's initial height.
     * @param windowTitle The game window's initial title bar text.
     */
    private SHLGL(int windowWidth, int windowHeight, String windowTitle) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        long windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
        if (windowHandle == NULL)
            throw new IllegalStateException("Failed to initialize game window!");
        this.gameWindow = new Window(windowHandle);
        GLFW.glfwMakeContextCurrent(windowHandle);
        this.eventBus = new EventBus();
        this.timer = new TickTimer();
        this.running = true;
    }

    /**
     * Shuts down the game engine.
     * @param exitCode The exit code to exit with.
     */
    public void shutdown(int exitCode) {
        running = false;
        this.exitCode = exitCode;
    }

    /**
     * Terminates GLFW and ends program execution.
     */
    private void terminate() {
        Callbacks.glfwFreeCallbacks(gameWindow.getHandle());
        GLFW.glfwDestroyWindow(gameWindow.getHandle());
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        Runtime.getRuntime().exit(exitCode);
    }

    /**
     * Retrieves the game window.
     * @return The game window.
     */
    public Window getGameWindow() {
        return gameWindow;
    }

    /**
     * Retrieves the event bus.
     * @return The event bus.
     */
    public EventBus getEventBus() {
        return eventBus;
    }

    /**
     * Begins the game's main loop.
     * @param tickRate The tick rate, in ticks per second.
     */
    public void runMainLoop(int tickRate) {
        timer.setTickRate(tickRate);
        timer.begin();
        while (running) {
            try {
                int elapsedTicks = timer.getElapsedTicks();
                for (int i = 0; i < elapsedTicks; i++)
                    eventBus.post(new GameTickEvent());
                eventBus.post(new RenderEvent());
            } catch (Exception e) {
                System.err.println("Exception thrown in main loop!");
                e.printStackTrace();
            }
        }
        timer.stop();
        terminate();
    }

}
