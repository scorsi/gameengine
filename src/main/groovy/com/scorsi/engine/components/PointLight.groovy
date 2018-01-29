package com.scorsi.engine.components

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardPointShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class PointLight extends BaseLight {

    private final int COLOR_DEPTH = 256

    /**
     * x: constant
     * y: linear
     * z: exponent
     */
    Vector3f attenuation
    float range

    PointLight(Vector3f color, float intensity, Vector3f attenuation) {
        super(color, intensity)
        this.attenuation = attenuation

        calculateRange()

        this.shader = ForwardPointShader.instance
    }

    private void calculateRange() {
        float a = attenuation.z
        float b = attenuation.y
        float c = attenuation.x - COLOR_DEPTH * intensity * color.max() as float

        range = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a) as float
    }

    void setConstant(float constant) {
        attenuation.x = constant
    }

    float getConstant() {
        return attenuation.x
    }

    void setLinear(float linear) {
        attenuation.y = linear
    }

    float getLinear() {
        return attenuation.y
    }

    void setExponent(float exponent) {
        attenuation.z = exponent
    }

    float getExponent() {
        return attenuation.z
    }

}
