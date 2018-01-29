package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class MeshRenderer extends GameComponent {

    Mesh mesh
    Material material

    MeshRenderer(Mesh mesh, Material material) {
        this.mesh = mesh
        this.material = material
    }

    void render(RenderingEngine renderingEngine, ShaderProgram shader) {
        shader.use().updateUniforms(renderingEngine, parent.transform, material)
        mesh.draw()
    }

}
