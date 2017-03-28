package io.github.phantamanta44.shlgl.render;

import io.github.phantamanta44.shlgl.texture.TextureManager;
import io.github.phantamanta44.shlgl.util.render.ShaderProperty;
import org.lwjgl.opengl.GL15;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.LinkedList;
import java.util.List;

/**
 * A graphics buffer containing instructions for rendering a frame.
 * @author Evan Geng
 */
public class RenderBuffer {

    /**
     * The queue of actions to run upon buffer flush.
     */
    private final List<Runnable> actions;

    /**
     * The vertex transformation kernel.
     */
    private final ShaderProperty.Mat4 trans;

    /**
     * The colour transformation vector.
     */
    private final ShaderProperty.Vec4 colour;

    /**
     * The rendering margin handler.
     */
    private final MarginHandler margins;

    /**
     * Creates a render buffer.
     * @param trans The transformation kernel uniform.
     * @param colour The colour modifier uniform.
     * @param margins The margin calculator.
     */
    public RenderBuffer(ShaderProperty.Mat4 trans, ShaderProperty.Vec4 colour, MarginHandler margins) {
        this.actions = new LinkedList<>();
        this.trans = trans;
        this.colour = colour;
        this.margins = margins;
    }

    /**
     * Draws a textured rectangle.
     * @param x The rectangle's x-coordinate.
     * @param y The rectangle's y-coordinate.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     * @param u The x texture coordinate.
     * @param v The y texture coordinate.
     * @param texW The texture width.
     * @param texH The texture height.
     */
    public void drawRect(float x, float y, float width, float height, float u, float v, float texW, float texH) {
        float a = TextureManager.getBound().w, b = TextureManager.getBound().h;
        float u1 = u / a, v1 = v / a;
        float u2 = (u + texW) / a, v2 = (v + texH) / b;
        float x1 = margins.computeX(x), y1 = margins.computeY(y);
        float x2 = margins.computeX(x + width), y2 = margins.computeY(y + height);
        buffer(() ->
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[] {
                    x2, y1, u2, v1,
                    x1, y1, u1, v1,
                    x2, y2, u2, v2,
                    x1, y2, u1, v2
            }, GL15.GL_STREAM_DRAW)
        );
    }

    /**
     * Draws a textured rectangle, assuming the entire texture is used.
     * @param x The rectangle's x-coordinate.
     * @param y The rectangle's y-coordinate.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void drawRect(float x, float y, float width, float height) {
        float x1 = margins.computeX(x), y1 = margins.computeY(y);
        float x2 = margins.computeX(x + width), y2 = margins.computeY(y + height);
        buffer(() ->
                GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[] {
                        x2, y1, 1F, 0F,
                        x1, y1, 0F, 0F,
                        x2, y2, 1F, 1F,
                        x1, y2, 0F, 1F
                }, GL15.GL_STREAM_DRAW)
        );
    }

    /**
     * Draws a textured line.
     * @param x Point A's x-coordinate.
     * @param y Point A's y-coordinate.
     * @param x2 Point B's x-coordinate.
     * @param y2 Point B's y-coordinate.
     * @param width The thickness of the line.
     */
    public void drawLine(float x, float y, float x2, float y2, float width) {
        throw new NotImplementedException(); // TODO Implement
    }

    /**
     * Sets the transformation colour.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
     */
    public void colour4F(float r, float g, float b, float a) {
        colour.set(r, g, b, a);
    }

    /**
     * Buffers an action for the next render tick.
     * @param action The action to buffer.
     */
    private void buffer(Runnable action) {
        actions.add(action);
    }

    /**
     * Runs all the buffered actions.
     */
    public void flush() {
        actions.forEach(Runnable::run);
        actions.clear();
    }

}
