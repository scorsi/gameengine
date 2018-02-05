package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ForwardAmbientShader extends ShaderProgram {

    static final ForwardAmbientShader instance = new ForwardAmbientShader()

    private ForwardAmbientShader() {
        super()

        attachShader(Shader.loadVertexShader("forward-ambient"))
        attachShader(Shader.loadFragmentShader("forward-ambient"))

        compile()

        addUniform("modelViewMatrix")
        addUniform("projectionMatrix")
        addUniform("ambientIntensity")
        addUniform("color")
        addUniform("useColor")
    }

    ShaderProgram updateUniforms(RenderingEngine renderingEngine, Transform transform, Material material) {
        def projectionMatrix = renderingEngine.transformation.getProjectionMatrix(Math.toRadians(60) as float, 800, 600, 0.01f, 1000)
        def viewMatrix = renderingEngine.transformation.getViewMatrix(renderingEngine.mainCamera.object.transform)
        def modelViewMatrix = renderingEngine.transformation.getModelViewMatrix(transform, viewMatrix)

        setUniform("projectionMatrix", projectionMatrix)
        setUniform("modelViewMatrix", modelViewMatrix)
        setUniform("ambientIntensity", renderingEngine.ambientLight)

        def texture = material.diffuseTexture
        if (texture != null) {
            texture.bind()
            setUniform("useColor", 0)
        } else {
            setUniform("color", material.diffuseColor)
            setUniform("useColor", 1)
        }

        return this
    }

}
