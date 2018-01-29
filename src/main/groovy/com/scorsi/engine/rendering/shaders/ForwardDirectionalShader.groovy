package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import com.scorsi.engine.rendering.lights.DirectionalLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ForwardDirectionalShader extends ShaderProgram {

    static final ForwardDirectionalShader instance = new ForwardDirectionalShader()
    DirectionalLight directionalLight

    private ForwardDirectionalShader() {
        super()

        attachShader(Shader.loadVertexShader("forward-directional.vert"))
        attachShader(Shader.loadFragmentShader("forward-directional.frag"))

        bindAttributeLocation(0, "position")
        bindAttributeLocation(1, "texCoord")
        bindAttributeLocation(2, "normal")

        compile()

        // Vert
        addUniform("model")
        addUniform("MVP")
        // Frag
        addUniform("specularIntensity")
        addUniform("specularPower")
        addUniform("eyePos")
        addDirectionalLightUniform("directionalLight")
    }

    ShaderProgram updateUniforms(Transform transform, Material material) {
        def worldMatrix = transform.transformation
        def projectedMatrix = renderingEngine.mainCamera.viewProjection * worldMatrix
        material.bind()

        setUniform("model", worldMatrix)
        setUniform("MVP", projectedMatrix)

        setUniform("specularIntensity", material.specularIntensity)
        setUniform("specularPower", material.specularPower)

        setUniform("eyePos", renderingEngine.mainCamera.position)
        setUniform("directionalLight", directionalLight)

        return this
    }

}
