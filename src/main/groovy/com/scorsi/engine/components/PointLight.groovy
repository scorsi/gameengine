package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.lights.Attenuation
import com.scorsi.engine.rendering.lights.BaseLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class PointLight extends GameComponent {

    BaseLight baseLight
    Vector3f position
    Attenuation attenuation
    float range

    PointLight(GameObject object, Vector3f color, float intensity, Vector3f position, Attenuation attenuation, float range) {
        this(object, new BaseLight(color, intensity), position, attenuation, range)
    }

    PointLight(GameObject object, BaseLight baseLight, Vector3f position, Attenuation attenuation, float range) {
        super(object)
        this.baseLight = baseLight
        this.position = position
        this.attenuation = attenuation
        this.range = range
    }

    void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.pointLights.add(this)
    }

}
