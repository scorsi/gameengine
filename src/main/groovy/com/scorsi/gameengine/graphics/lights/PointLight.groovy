package com.scorsi.gameengine.graphics.lights

import com.scorsi.gameengine.math.Vector3f
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