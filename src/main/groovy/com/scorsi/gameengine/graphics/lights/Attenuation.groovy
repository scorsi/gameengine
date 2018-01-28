package com.scorsi.gameengine.graphics.lights

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
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
