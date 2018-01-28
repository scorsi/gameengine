package com.scorsi.gameengine.graphics.lights

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class SpotLight extends PointLight {

    Vector3f direction
    float cutoff

    SpotLight(Vector3f color, float intensity, Vector3f position, Attenuation attenuation, float range, Vector3f direction, float cutoff) {
        super(color, intensity, position, attenuation, range)
        this.direction = direction.normalize()
        this.cutoff = cutoff
    }

}
