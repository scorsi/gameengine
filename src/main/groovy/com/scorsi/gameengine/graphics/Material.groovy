package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Material {

    Texture texture
    Vector3f color
    float specularIntensity
    float specularPower

    Material(Texture texture, Vector3f color, float specularIntensity = 2, float specularPower = 32) {
        this.texture = texture
        this.color = color
        this.specularIntensity = specularIntensity
        this.specularPower = specularPower
    }

    void bind() {
        this.texture.bind()
    }

}
