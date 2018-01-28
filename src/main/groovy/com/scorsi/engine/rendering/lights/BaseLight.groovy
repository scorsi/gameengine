package com.scorsi.engine.rendering.lights

import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class BaseLight {

    Vector3f color
    float intensity

    BaseLight(Vector3f color, float intensity) {
        this.color = color
        this.intensity = intensity
    }

}
