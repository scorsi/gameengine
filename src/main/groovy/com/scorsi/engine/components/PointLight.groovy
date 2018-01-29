package com.scorsi.engine.components

import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardPointShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class PointLight extends BaseLight {

    Vector3f position
    float constant
    float linear
    float exponent
    float range

    PointLight(GameObject object, Vector3f color, float intensity, Vector3f position, float constant, float linear, float exponent, float range) {
        super(object, color, intensity)
        this.position = position
        this.constant = constant
        this.linear = linear
        this.exponent = exponent
        this.range = range

        this.shader = ForwardPointShader.instance
    }

}
