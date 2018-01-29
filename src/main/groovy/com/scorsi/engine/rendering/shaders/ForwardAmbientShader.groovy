package com.scorsi.engine.rendering.shaders

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

        attachShader(Shader.loadVertexShader("forward-ambient.vert"))
        attachShader(Shader.loadFragmentShader("forward-ambient.frag"))

        bindAttributeLocation(0, "position")
        bindAttributeLocation(1, "texCoord")

        compile()

        addUniform("MVP")
        addUniform("ambientIntensity")
    }

    ShaderProgram updateUniforms(Transform transform, Material material) {
        def projectedMatrix = renderingEngine.mainCamera.viewProjection * transform.transformation
        material.bind()

        setUniform("MVP", projectedMatrix)
        setUniform("ambientIntensity", renderingEngine.ambientLight)

        return this
    }

}
