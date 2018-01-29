package com.scorsi.engine.components

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardSpotShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class SpotLight extends PointLight {

    Vector3f direction
    float cutoff

    SpotLight(Vector3f color, float intensity, Vector3f attenuation, Vector3f direction, float cutoff) {
        super(color, intensity, attenuation)
        this.direction = direction.normalize()
        this.cutoff = cutoff

        this.shader = ForwardSpotShader.instance
    }

}
