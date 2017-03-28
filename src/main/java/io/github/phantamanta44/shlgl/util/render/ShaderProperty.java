package io.github.phantamanta44.shlgl.util.render;

/**
 * Represents a <code>uniform</code> property of a shader.
 * @author Evan Geng
 */
public class ShaderProperty {

    /**
     * The shader program containing this property.
     */
    protected final int shaderProg;

    /**
     * The location of this property.
     */
    protected final int propInd;

    /**
     * Creates a ShaderProperty for the given shader and property index.
     * @param shaderProg The shader program comtaining the property.
     * @param propInd The property's location.
     */
    public ShaderProperty(int shaderProg, int propInd) {
        this.shaderProg = shaderProg;
        this.propInd = propInd;
    }

    /**
     * Sets the value of this shader property.
     * @param values The new values to populate the property with.
     */
    public void set(float[] values) {

    }

}
