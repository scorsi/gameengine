package com.scorsi.engine.components

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardSpotShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

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
        parent.transform.rotation.forward
    }

}
