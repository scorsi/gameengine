package com.scorsi.gameengine.graphics.lights

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.ToString

@ToString(includePackage = false, includeNames = true)
class BaseLight {

    Vector3f color
    float intensity

    BaseLight(Vector3f color, float intensity) {
        this.color = color
        this.intensity = intensity
    }

}
