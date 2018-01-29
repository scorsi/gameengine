package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.lights.BaseLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class DirectionalLight extends GameComponent {

    BaseLight baseLight
    Vector3f direction

    DirectionalLight(GameObject object, Vector3f color, float intensity, Vector3f direction) {
        this(object, new BaseLight(color, intensity), direction)
    }

    DirectionalLight(GameObject object, BaseLight baseLight, Vector3f direction) {
        super(object)
        this.baseLight = baseLight
        this.direction = direction.normalize()
    }

    void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.directionalLights.add(this)
    }

}
