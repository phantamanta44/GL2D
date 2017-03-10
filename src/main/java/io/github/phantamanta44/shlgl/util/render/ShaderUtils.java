package io.github.phantamanta44.shlgl.util.render;

import org.lwjgl.opengl.GL20;

/**
 * Utility class for working with OpenGL shaders.
 * @author Evan Geng
 */
public class ShaderUtils {

    /**
     * Compiles a shader for use by the GPU.
     * @param type The shader type; either {@link GL20#GL_VERTEX_SHADER} or {@link GL20#GL_FRAGMENT_SHADER}
     * @param source The shader source code.
     * @return The new shader's handle.
     */
    public static int compileShader(int type, String source) {
        int shader = GL20.glCreateShader(type);
        GL20.glShaderSource(shader, source);
        GL20.glCompileShader(shader);
        return shader;
    }

    /**
     * Links shaders into a program.
     * @param shaders The handles of the shaders.
     * @return The new program's handle.
     */
    public static int createProgram(int... shaders) {
        int program = GL20.glCreateProgram();
        for (int shader : shaders)
            GL20.glAttachShader(program, shader);
        GL20.glLinkProgram(program);
        return program;
    }

}
