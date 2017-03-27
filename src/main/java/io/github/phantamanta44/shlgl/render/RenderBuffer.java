package io.github.phantamanta44.shlgl.render;

import io.github.phantamanta44.shlgl.SHLGL;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A graphics buffer containing instructions for rendering a frame.
 * @author Evan Geng
 */
public class RenderBuffer {

    /**
     * The parent SHLGL instance.
     */
    private final SHLGL shlgl;

    /**
     * Creates a render buffer.
     * @param shlgl The SHLGL instance under which this buffer should run.
     */
    public RenderBuffer(SHLGL shlgl) {
        this.shlgl = shlgl;
    }

    // TODO Drawing methods

    /**
     * Sets the transformation colour.
     * @param r The red component.
     * @param g The green component.
     * @param b The blue component.
     * @param a The alpha component.
     */
    public void color4F(float r, float g, float b, float a) {
        throw new NotImplementedException(); // TODO Implement
    }

    /**
     * Retrieves the total number of VBOs required to draw this buffer.
     * @return The VBO count.
     */
    public int getBufferCount() {
        throw new NotImplementedException(); // TODO Implement
    }

    /**
     * Retrieves the vertex data for a specific VBO.
     * @return The VBO data.
     */
    public float[] getBuffer(int index) {
        throw new NotImplementedException(); // TODO Implement
    }

}
