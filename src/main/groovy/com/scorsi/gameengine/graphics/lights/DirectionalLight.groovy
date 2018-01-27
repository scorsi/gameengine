package com.scorsi.gameengine.graphics.lights

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.ToString

@ToString(includePackage = false, includeNames = true)
class DirectionalLight extends BaseLight {

    Vector3f direction

    DirectionalLight(Vector3f color, float intensity, Vector3f direction) {
        super(color, intensity)
        this.direction = direction.normalize()
    }

}
