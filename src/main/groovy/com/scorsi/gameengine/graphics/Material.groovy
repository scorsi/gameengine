package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic

@CompileStatic
class Material {

    Texture texture
    Vector3f color

    Material(Texture texture, Vector3f color) {
        this.texture = texture
        this.color = color
    }

    void bind() {
        this.texture.bind()
    }

}
