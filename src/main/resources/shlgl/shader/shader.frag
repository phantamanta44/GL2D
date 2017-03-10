#version 150

in vec2 texPos;

out vec4 colour;

uniform sampler2D tex;
uniform vec4 colourTransform;

void main() {
    colour = texture(tex, texPos) * colourTransform;
}