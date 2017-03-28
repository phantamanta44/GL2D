package io.github.phantamanta44.shlgl.util.render;

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

    // TODO Implement

}
