package com.scorsi.engine.components

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.lights.BaseLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class DirectionalLight {

    BaseLight baseLight
    Vector3f direction

    DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
        this(new BaseLight(color, intensity), direction)
    }

    DirectionalLight(BaseLight baseLight, Vector3f direction) {
        this.baseLight = baseLight
        this.direction = direction.normalize()
    }

}
