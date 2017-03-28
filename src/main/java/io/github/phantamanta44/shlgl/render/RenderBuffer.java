package io.github.phantamanta44.shlgl.render;

import io.github.phantamanta44.shlgl.util.render.ShaderProperty;
import io.github.phantamanta44.shlgl.util.render.TexConsumer;
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
     * The texture sampler.
     */
    private final TexConsumer tex;

    /**
     * Creates a render buffer.
     * @param trans The transformation kernel uniform.
     * @param colour The colour modifier uniform.
     * @param tex The texture sampler uniform.
     */
    public RenderBuffer(ShaderProperty.Mat4 trans, ShaderProperty.Vec4 colour, TexConsumer tex) {
        this.actions = new LinkedList<>();
        this.trans = trans;
        this.colour = colour;
        this.tex = tex;
    }

    /**
     * Draws a textured rectangle.
     * @param x The rectangle's x-coordinate.
     * @param y The rectangle's y-coordinate.
     * @param width The rectangle's width.
     * @param height The rectangle's height.
     */
    public void drawRect(float x, float y, float width, float height) {
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
        throw new NotImplementedException(); // TODO Implement
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
