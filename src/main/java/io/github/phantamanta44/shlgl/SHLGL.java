package io.github.phantamanta44.shlgl;

import io.github.phantamanta44.shlgl.event.EventBus;
import io.github.phantamanta44.shlgl.event.impl.GameTickEvent;
import io.github.phantamanta44.shlgl.event.impl.RenderEvent;
import io.github.phantamanta44.shlgl.game.TickTimer;
import io.github.phantamanta44.shlgl.render.MarginHandler;
import io.github.phantamanta44.shlgl.render.RenderBuffer;
import io.github.phantamanta44.shlgl.render.Window;
import io.github.phantamanta44.shlgl.util.io.InputStreamUtils;
import io.github.phantamanta44.shlgl.util.io.ResourceUtils;
import io.github.phantamanta44.shlgl.util.math.Vector2I;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import io.github.phantamanta44.shlgl.util.render.ShaderProperty;
import io.github.phantamanta44.shlgl.util.render.ShaderUtils;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.io.IOException;
import java.io.InputStream;

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
     * The game window handle.
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
     * The shader program used for rendering.
     */
    private int shaderProg;

    /**
     * The transformation kernel uniform.
     */
    private ShaderProperty.Mat4 trans;

    /**
     * The colour modifier uniform.
     */
    private ShaderProperty.Vec4 colourTrans;

    /**
     * The rendering action buffer.
     */
    private RenderBuffer renderBuffer;

    /**
     * The resolution width.
     */
    private int width;

    /**
     * The resolution height.
     */
    private int height;

    /**
     * The resolution-conservation margin handler instance.
     */
    private MarginHandler margins;

    /**
     * The address of the VBO used for rendering.
     */
    private int vbo;

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
        this.windowHandle = GLFW.glfwCreateWindow(windowWidth, windowHeight, windowTitle, NULL, NULL);
        if (windowHandle == NULL)
            throw new IllegalStateException("Failed to initialize game window!");
        GLFW.glfwMakeContextCurrent(windowHandle);
        this.gameWindow = new Window(windowHandle);
        GL.createCapabilities();
        vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        initShaders();
        setResolution(640, 480);
        this.eventBus = new EventBus();
        this.timer = new TickTimer();
        this.running = true;
    } // TODO Audio

    /**
     * Initializes the default vertex and fragment shaders.
     */
    private void initShaders() {
        try (InputStream vertIn = ResourceUtils.getStream("shlgl/shader/shader.vert");
             InputStream fragIn = ResourceUtils.getStream("shlgl/shader/shader.frag")) {
            int vert = ShaderUtils.compileShader(GL20.GL_VERTEX_SHADER, InputStreamUtils.readAsString(vertIn));
            int frag = ShaderUtils.compileShader(GL20.GL_FRAGMENT_SHADER, InputStreamUtils.readAsString(fragIn));
            shaderProg = ShaderUtils.createProgram(vert, frag);
            GL20.glUseProgram(shaderProg);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize shaders!", e);
        }
        int loc = GL20.glGetAttribLocation(shaderProg, "posXY");
        GL20.glVertexAttribPointer(loc, 2, GL11.GL_FLOAT, false, Float.BYTES * 4, 0);
        GL20.glEnableVertexAttribArray(loc);
        loc = GL20.glGetAttribLocation(shaderProg, "posUV");
        GL20.glVertexAttribPointer(loc, 2, GL11.GL_FLOAT, false, Float.BYTES * 4, Float.BYTES * 2);
        GL20.glEnableVertexAttribArray(loc);
        loc = GL20.glGetUniformLocation(shaderProg, "transformKernel");
        trans = new ShaderProperty.Mat4(shaderProg, loc);
        loc = GL20.glGetUniformLocation(shaderProg, "colourTransform");
        colourTrans = new ShaderProperty.Vec4(shaderProg, loc);
    }

    /**
     * Sets the game resolution. 640x480 by default.
     */
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
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
        margins = new MarginHandler();
        renderBuffer = new RenderBuffer(trans, colourTrans, margins);
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
                running = false;
            }
        }
        timer.stop();
        terminate();
    }

    /**
     * Buffers one frame to be rendered.
     */
    private void render() {
        try (Pooled<Vector2I> size = gameWindow.getSize()) {
            margins.update(size.get(), width, height);
        }
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        eventBus.post(new RenderEvent(renderBuffer));
        renderBuffer.flush();
        GLFW.glfwSwapBuffers(windowHandle);
    }

}
