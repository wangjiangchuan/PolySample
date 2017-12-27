#version 300 es
precision mediump float;
uniform sampler2D textureImage;
in vec2 vTexCoords;
out vec4 fragColor;
void main() {
  fragColor = texture(textureImage, vTexCoords);
}