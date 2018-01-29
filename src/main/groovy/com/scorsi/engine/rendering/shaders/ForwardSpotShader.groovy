package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import com.scorsi.engine.rendering.lights.SpotLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ForwardSpotShader extends ShaderProgram {

    static final ForwardSpotShader instance = new ForwardSpotShader()
    SpotLight spotLight

    private ForwardSpotShader() {
        super()

        attachShader(Shader.loadVertexShader("forward-spot.vert"))
        attachShader(Shader.loadFragmentShader("forward-spot.frag"))

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
        addSpotLightUniform("spotLight")
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
        setUniform("spotLight", spotLight)

        return this
    }

}
