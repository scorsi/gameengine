package com.scorsi.engine.rendering.shaders

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
//        // TODO: Change this to not recompute it
//        def projectionMatrix = renderingEngine.transformation.getProjectionMatrix(Math.toRadians(60) as float, 800, 600, 0.01f, 1000)
//        def worldMatrix = renderingEngine.transformation.getWorldMatrix(transform.position, transform.rotation, transform.scale)
//        material.getSafeTexture("diffuse").bind()
//
//        setUniform("model", worldMatrix)
//        setUniform("MVP", projectionMatrix)
//
//        setUniform("specularIntensity", material.getSafeFloat("specularIntensity"))
//        setUniform("specularPower", material.getSafeFloat("specularPower"))
//
//        setUniform("eyePos", new Vector3f(0,0,0))
//        setUniform("pointLight", renderingEngine.activeLight as PointLight)

        return this
    }

}
