package com.scorsi.engine.components

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.lights.Attenuation
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class SpotLight {

    PointLight pointLight
    Vector3f direction
    float cutoff

    SpotLight(Vector3f color, float intensity, Vector3f position, Attenuation attenuation, float range, Vector3f direction, float cutoff) {
        this(new PointLight(color, intensity, position, attenuation, range), direction, cutoff)
    }

    SpotLight(PointLight pointLight, Vector3f direction, float cutoff) {
        this.pointLight = pointLight
        this.direction = direction.normalize()
        this.cutoff = cutoff
    }

}
