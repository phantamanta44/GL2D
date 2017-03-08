package io.github.phantamanta44.shlgl;

import io.github.phantamanta44.shlgl.event.EventBus;
import io.github.phantamanta44.shlgl.event.impl.RenderEvent;
import io.github.phantamanta44.shlgl.event.impl.GameTickEvent;
import io.github.phantamanta44.shlgl.game.TickTimer;
import io.github.phantamanta44.shlgl.render.RenderBuffer;
import io.github.phantamanta44.shlgl.render.Window;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

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
     * Initializes GLFW and constructs the game render.
     * @param windowWidth The game render's initial width.
     * @param windowHeight The game render's initial height.
     * @param windowTitle The game render's initial title bar text.
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
     * The game render instance.
     */
    private final Window gameWindow;

    /**
     * The game render handle.
     */
    private final long windowHandle;

    /**
     * The game event bus.
     */
    private final EventBus eventBus;

    /**
     * The game tick timer.
     */
    private final TickTimer timer;

    /**
     * The game tick counter.
     */
    private long tickCount;

    /**
     * The game's running state.
     */
    private boolean running;

    /**
     * The exit code to terminate with.
     */
    private int exitCode = 1;

    /**
     * Initializes GLFW and constructs the game render.
     * @param windowWidth The game render's initial width.
     * @param windowHeight The game render's initial height.
     * @param windowTitle The game render's initial title bar text.
     */
    private SHLGL(int windowWidth, int windowHeight, String windowTitle) {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit())
            throw new IllegalStateException("Failed to initialize GLFW!");
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
        this.windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
        if (windowHandle == NULL)
            throw new IllegalStateException("Failed to initialize game render!");
        this.gameWindow = new Window(windowHandle);
        GLFW.glfwMakeContextCurrent(windowHandle);
        GL.createCapabilities();
        // TODO Initialize shaders
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
        Callbacks.glfwFreeCallbacks(windowHandle);
        GLFW.glfwDestroyWindow(windowHandle);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
        Runtime.getRuntime().exit(exitCode);
    }

    /**
     * Retrieves the game render.
     * @return The game render.
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
        tickCount = 0;
        while (running) {
            try {
                int elapsedTicks = timer.getElapsedTicks();
                for (int i = 0; i < elapsedTicks; i++) {
                    eventBus.post(new GameTickEvent(tickCount));
                    tickCount++;
                }
                render();
                GLFW.glfwPollEvents();
            } catch (Exception e) {
                System.err.println("Exception thrown in main loop!");
                e.printStackTrace();
            }
        }
        timer.stop();
        terminate();
    }

    /**
     * Buffers one frame to be rendered.
     */
    private void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        RenderBuffer buffer = new RenderBuffer();
        eventBus.post(new RenderEvent(buffer));
        int[] vbos = new int[buffer.getBufferCount()];
        GL15.glGenBuffers(vbos);
        for (int i = 0; i < vbos.length; i++) {
            GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[i]);
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer.getBuffer(i), GL15.GL_STATIC_DRAW);
        }
        GLFW.glfwSwapBuffers(windowHandle);
    }

}
