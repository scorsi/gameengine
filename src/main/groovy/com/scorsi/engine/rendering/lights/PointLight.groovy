package com.scorsi.engine.rendering.lights

import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class PointLight extends BaseLight {

    Vector3f position
    Attenuation attenuation
    float range

    PointLight(Vector3f color, float intensity, Vector3f position, Attenuation attenuation, float range) {
        super(color, intensity)
        this.position = position
        this.attenuation = attenuation
        this.range = range
    }
}
