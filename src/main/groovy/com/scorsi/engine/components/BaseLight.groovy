package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class BaseLight extends GameComponent {

    ShaderProgram shader
    Vector3f color
    float intensity

    BaseLight(GameObject object, Vector3f color, float intensity) {
        super(object)
        this.color = color
        this.intensity = intensity
    }

    void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.lights.add(this)
    }

}
