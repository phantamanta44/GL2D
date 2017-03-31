package io.github.phantamanta44.shlgl.graphics.render;

import io.github.phantamanta44.shlgl.graphics.texture.TextureInfo;
import io.github.phantamanta44.shlgl.graphics.texture.TextureManager;
import io.github.phantamanta44.shlgl.util.collection.StackNode;
import io.github.phantamanta44.shlgl.util.math.Matrix4F;
import io.github.phantamanta44.shlgl.util.memory.Pooled;
import org.lwjgl.opengl.GL11;
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
     * Pi, represented as a float.
     */
    private static final float PI_FLOAT = 3.14159265F;

    /**
     * The queue of actions to run upon buffer flush.
     */
    private final List<Runnable> actions;

    /**
     * The vertex transformation kernel property.
     */
    private final ShaderProperty.Mat4 trans;

    /**
     * The colour transformation vector property.
     */
    private final ShaderProperty.Vec4 colour;

    /**
     * The rendering margin handler.
     */
    private final MarginHandler margins;

    /**
     * The transformation kernel.
     */
    private Pooled<Matrix4F> kernel;

    /**
     * The transformation kernel state stack.
     */
    private StackNode<float[]> kernelStack;

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
        this.kernel = Matrix4F.ident();
        this.kernelStack = new StackNode<>();
    }

    /**
     * Binds a texture to be rendered.
     * @param tex The texture.
     */
    public void bind(TextureInfo tex) {
        TextureManager.bind(tex);
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
        buffer(() -> {
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[]{
                    x2, y1, u2, v1,
                    x1, y1, u1, v1,
                    x2, y2, u2, v2,
                    x1, y2, u1, v2
            }, GL15.GL_STREAM_DRAW);
            GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
        });
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
        buffer(() -> {
            GL15.glBufferData(GL15.GL_ARRAY_BUFFER, new float[]{
                    x2, y1, 1F, 0F,
                    x1, y1, 0F, 0F,
                    x2, y2, 1F, 1F,
                    x1, y2, 0F, 1F
            }, GL15.GL_STREAM_DRAW);
            GL11.glDrawArrays(GL11.GL_QUADS, 0, 4);
        });
    }

    /**
     * Draws a textured line.
     * @param x Point A's x-coordinate.
     * @param y Point A's y-coordinate.
     * @param x2 Point B's x-coordinate.
     * @param y2 Point B's y-coordinate.
     * @param width The thickness of the line.
     * @param u The x texture coordinate.
     * @param v The y texture coordinate.
     * @param texW The texture width.
     * @param texH The texture height.
     */
    public void drawLine(float x, float y, float x2, float y2, float width, float u, float v, float texW, float texH) {
        throw new NotImplementedException(); // TODO Implement
    }

    /**
     * Draws a textured line, assuming the entire texture is used.
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
     * Pushes the current transformation kernel to the stack, storing its state.
     */
    public void pushMatrix() {
        kernelStack = kernelStack.extend(kernel.get().asArray());
    }

    /**
     * Pops the topmost element of the transformation kernel stack and restores the stored state.
     */
    public void popMatrix() {
        if (kernelStack.hasParent()) {
            kernel.get().readArray(kernelStack.getValue());
            kernelStack = kernelStack.getParent();
        }
    }

    /**
     * Scales the render by a factor.
     * @param x The horizontal scaling factor.
     * @param y The vertical scaling factor.
     */
    public void scale(float x, float y) {
        multiply(
                x, 0F, 0F, 0F,
                0F, y, 0F, 0F,
                0F, 0F, 1F, 0F,
                0F, 0F, 0F, 1F
        );
    }

    /**
     * Translates the render by an offset.
     * @param x The x offset.
     * @param y The y offset.
     */
    public void translate(float x, float y) {
        multiply(
                1F, 0F, 0F, x,
                0F, 1F, 0F, y,
                0F, 0F, 1F, 0F,
                0F, 0F, 0F, 1F
        );
    }

    /**
     * Rotates the render by an angle.
     * @param degrees The angle of rotation, in degrees.
     * @param x The x component of the rotation axis vector.
     * @param y The y component of the rotation axis vector.
     */
    public void rotate(float degrees, float x, float y) {
        float radians = degrees * PI_FLOAT / 180F;
        float sin = (float)Math.sin(radians), cos = (float)Math.cos(radians);
        multiply(
                x * x * (1F - cos) + cos, y * x * (1F - cos), y * sin, 0F,
                x * y * (1F - cos), y * y * (1F - cos) + cos, -x * sin, 0F,
                -y * sin, x * sin, cos, 0F,
                0F, 0F, 0F, 1F
        );
    }

    /**
     * Multiplies the transformation kernel by the given matrix.
     * @param values The values of the multiplier matrix.
     */
    private void multiply(float... values) {
        buffer(() -> {
            try (Pooled<Matrix4F> mat = Matrix4F.of(values)) {
                kernel.get().multiply(mat.get());
            }
            trans.set(kernel.get().asArray());
        });
    }

    /**
     * Sets the colour modifier.
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
        kernel.free();
        kernel = Matrix4F.ident();
    }

}
