package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class BasicShader extends ShaderProgram {

    static final BasicShader instance = new BasicShader()

    private BasicShader() {
        super()

        attachShader(Shader.loadVertexShader("basic.vert"))
        attachShader(Shader.loadFragmentShader("basic.frag"))
        link()

        addUniform("transform")
        addUniform("color")
    }

    ShaderProgram updateUniforms(Transform transform, Material material) {
        def projectedMatrix = renderingEngine.mainCamera.viewProjection * transform.transformation
        material.bind()
        return setUniform("transform", projectedMatrix)
                .setUniform("color", material.color)
    }

}
