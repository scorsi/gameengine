package com.scorsi.engine.components

import com.scorsi.engine.rendering.shaders.ForwardSpotShader
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class SpotLight extends PointLight {

    float cutoff

    SpotLight(Vector3f color, float intensity, Vector3f attenuation, float cutoff) {
        super(color, intensity, attenuation)
        this.cutoff = cutoff

        this.shader = ForwardSpotShader.instance
    }

    Vector3f getDirection() {
        new Vector3f(0, 0, 1)//.rotate(parent.transform.rotation)
    }

}
