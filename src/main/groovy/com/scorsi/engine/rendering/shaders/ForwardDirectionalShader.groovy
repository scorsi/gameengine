package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.components.DirectionalLight
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ForwardDirectionalShader extends ShaderProgram {

    static final ForwardDirectionalShader instance = new ForwardDirectionalShader()

    private ForwardDirectionalShader() {
        super()

        attachShader(Shader.loadVertexShader("forward-directional"))
        attachShader(Shader.loadFragmentShader("forward-directional"))

//        bindAttributeLocation(0, "position")
//        bindAttributeLocation(1, "texCoord")
//        bindAttributeLocation(2, "normal")

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

    ShaderProgram updateUniforms(RenderingEngine renderingEngine, Transform transform, Material material) {
        def worldMatrix = transform.transformation
        def projectedMatrix = renderingEngine.mainCamera.viewProjection * worldMatrix
        material.getSafeTexture("diffuse").bind()

        setUniform("model", worldMatrix)
        setUniform("MVP", projectedMatrix)

        setUniform("specularIntensity", material.getSafeFloat("specularIntensity"))
        setUniform("specularPower", material.getSafeFloat("specularPower"))

        setUniform("eyePos", renderingEngine.mainCamera.parent.transform.translation)
        setUniform("directionalLight", renderingEngine.activeLight as DirectionalLight)

        return this
    }

}
