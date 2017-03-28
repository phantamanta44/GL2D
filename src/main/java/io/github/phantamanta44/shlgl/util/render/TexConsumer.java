package io.github.phantamanta44.shlgl.util.render;

import org.lwjgl.opengl.GL11;

/**
 * Consumes texture data and binds it to the renderer.
 * @author Evan Geng
 */
public class TexConsumer {

    /**
     * The shader program.
     */
    private final int shaderProg;

    /**
     * The uniform's location.
     */
    private final int loc;

    /**
     * Creates a TexConsumer for the given shader program at the given uniform location.
     * @param shaderProg The shader program.
     * @param loc The uniform's location.
     */
    public TexConsumer(int shaderProg, int loc) {
        this.shaderProg = shaderProg;
        this.loc = loc;
    }

    /**
     * Binds a texture by ID.
     * @param id The texture ID to bind.
     */
    public void bind(int id) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

}
