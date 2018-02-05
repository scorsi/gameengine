package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.components.PointLight
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ForwardPointShader extends ShaderProgram {

    static final ForwardPointShader instance = new ForwardPointShader()

    private ForwardPointShader() {
        super()

        attachShader(Shader.loadVertexShader("forward-point"))
        attachShader(Shader.loadFragmentShader("forward-point"))

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
        addPointLightUniform("pointLight")
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
        setUniform("pointLight", renderingEngine.activeLight as PointLight)

        return this
    }

}
