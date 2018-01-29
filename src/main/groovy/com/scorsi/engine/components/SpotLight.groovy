package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.lights.Attenuation
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class SpotLight extends GameComponent {

    PointLight pointLight
    Vector3f direction
    float cutoff

    SpotLight(GameObject object, Vector3f color, float intensity, Vector3f position, Attenuation attenuation, float range, Vector3f direction, float cutoff) {
        this(object, new PointLight(object, color, intensity, position, attenuation, range), direction, cutoff)
    }

    SpotLight(GameObject object, PointLight pointLight, Vector3f direction, float cutoff) {
        super(object)
        this.pointLight = pointLight
        this.direction = direction.normalize()
        this.cutoff = cutoff
    }

    void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.spotLights.add(this)
    }

}
