#version 330

in vec2 texCoord0;

uniform vec3 ambientIntensity;
uniform sampler2D sampler;
uniform vec3 color;
uniform int useColor;

out vec4 fragColor;

void main() {
    if (useColor == 1) {
        fragColor = vec4(color, 1);
    } else {
        fragColor = texture(sampler, texCoord0.xy) * vec4(ambientIntensity, 1);
    }
}
