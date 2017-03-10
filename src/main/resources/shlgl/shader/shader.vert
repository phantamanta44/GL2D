#version 150

in vec2 posXY;
in vec2 posUV;

out vec2 texPos;

uniform mat4 transformKernel;

void main() {
    gl_Position = vec4(posXY, 0.0, 1.0) * transformKernel;
    texPos = posUV;
}