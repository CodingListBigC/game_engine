#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;

out vec2 TexCoord;

uniform mat4 modelMatrix;      // The matrix we just sent
uniform mat4 projection;
uniform mat4 view;
uniform mat4 model; // This now contains Position AND Rotation

void main() {
    gl_Position = projection * view * model * modelMatrix* vec4(aPos, 1.0);
    TexCoord = aTexCoord;
}