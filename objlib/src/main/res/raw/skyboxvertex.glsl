#version 300 es
layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texcoords;
out vec2 texCoords;
uniform mat4 modelMatrix;
uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

float gradient = 1.5;
float density = 0.04;

out float visibility;

void main(void){
	texCoords = texcoords;
	vec4 worldPosition = modelMatrix * vec4(position, 1.0);
	vec4 toCameraPos = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * toCameraPos;
	
	float distance = length(toCameraPos.xyz);
    visibility = exp(-pow((min(distance, 30.0) * density),gradient));
    visibility = clamp(visibility, 0.0, 1.0);
	
	//gl_Position = projectionMatrix * viewMatrix * modelMatrix * vec4(position, 1.0);
}