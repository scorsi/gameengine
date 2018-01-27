package com.scorsi.gameengine.graphics.lights

class Attenuation {

    float constant
    float linear
    float exponent

    Attenuation(float constant, float linear, float exponent) {
        this.constant = constant
        this.linear = linear
        this.exponent = exponent
    }

}
