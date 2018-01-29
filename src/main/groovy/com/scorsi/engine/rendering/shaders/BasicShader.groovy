package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.RenderUtil
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

    ShaderProgram updateUniforms(Matrix4f worldMatrix, Matrix4f projectedMatrix, Material material) {
        if (material.texture != null)
            material.bind()
        else
            RenderUtil.unbindTextures()

        return setUniform("transform", projectedMatrix)
                .setUniform("color", material.getColor())
    }

}
