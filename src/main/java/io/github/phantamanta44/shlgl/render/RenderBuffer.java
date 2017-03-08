package io.github.phantamanta44.shlgl.render;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * A graphics buffer containing instructions for rendering a frame.
 * @author Evan Geng
 */
public class RenderBuffer {

    // TODO Drawing methods

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
