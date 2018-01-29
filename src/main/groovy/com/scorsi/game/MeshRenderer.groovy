package com.scorsi.game

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.shaders.BasicShader
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

    void render() {
        BasicShader.instance
                .updateUniforms(object.transform.transformation, object.transform.projectedTransformation, material)
                .use()
        mesh.draw()
    }

}
