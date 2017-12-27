#version 300 es
in vec2 texCoords;
in float visibility;
out vec4 out_colour;

uniform sampler2D textureImage;

vec3 skyColor = vec3(0.5, 0.5, 0.5);

void main(void){
	out_colour = texture(textureImage, texCoords); //vec4(0.0, 1.0, 0.0, 1.0);//
	out_colour = mix(vec4(skyColor, 1.0), out_colour, visibility);
	//out_colour = vec4(1.0, 1.0, 0.0, 1.0);
}