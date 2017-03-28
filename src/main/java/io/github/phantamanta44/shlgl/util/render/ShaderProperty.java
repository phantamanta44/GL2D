package io.github.phantamanta44.shlgl.util.render;

import org.lwjgl.opengl.GL20;

/**
 * Represents a <code>uniform</code> property of a shader.
 * @author Evan Geng
 */
public abstract class ShaderProperty {

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
    public abstract void set(float[] values);

    /**
     * Represents a <code>vec4</code> uniform.
     */
    public static class Vec4 extends ShaderProperty {

        /**
         * Creates a vec4 property for the given shader and property index.
         * @param shaderProg The shader program comtaining the property.
         * @param propInd The property's location.
         */
        public Vec4(int shaderProg, int propInd) {
            super(shaderProg, propInd);
        }

        @Override
        public void set(float[] values) {
            GL20.glUniform4fv(propInd, values);
        }

    }

    /**
     * Represents a <code>mat4</code> uniform.
     */
    public static class Mat4 extends ShaderProperty {

        /**
         * Creates a mat4 property for the given shader and property index.
         * @param shaderProg The shader program comtaining the property.
         * @param propInd The property's location.
         */
        public Mat4(int shaderProg, int propInd) {
            super(shaderProg, propInd);
        }

        @Override
        public void set(float[] values) {
            GL20.glUniformMatrix4fv(propInd, true, values);
        }

    }

}
