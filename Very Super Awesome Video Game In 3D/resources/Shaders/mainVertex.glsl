#version 330

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;
layout(location = 2) in vec3 absolutePosition;

out vec3 fragColor;

void main() {
    gl_Position = vec4(position, 1.0);
    fragColor = color+absolutePosition;
}