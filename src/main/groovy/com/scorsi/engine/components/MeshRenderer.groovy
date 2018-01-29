package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.shaders.BasicShader
import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class MeshRenderer extends GameComponent {

    Mesh mesh
    Material material

    MeshRenderer(GameObject object, Mesh mesh, Material material) {
        super(object)
        this.mesh = mesh
        this.material = material
    }

    void render(ShaderProgram shader) {
        shader.use().updateUniforms(object.transform, material)
        mesh.draw()
    }

}
