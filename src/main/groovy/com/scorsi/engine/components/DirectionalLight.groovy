package com.scorsi.engine.components

import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardDirectionalShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class DirectionalLight extends BaseLight {

    Vector3f direction

    DirectionalLight(GameObject object, Vector3f color, float intensity, Vector3f direction) {
        super(object, color, intensity)
        this.direction = direction.normalize()

        this.shader = ForwardDirectionalShader.instance
    }

}