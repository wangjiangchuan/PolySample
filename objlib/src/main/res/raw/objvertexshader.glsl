#version 300 es
layout (location = 0) in vec4 aPosition;
layout (location = 1) in vec4 aColor;

out vec4 vColor;

uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

void main() {
    vColor = aColor;
    gl_Position =  projectionMatrix * viewMatrix * modelMatrix * aPosition;
}