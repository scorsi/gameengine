package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector2f
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Material {

    Texture diffuseTexture
    Vector3f diffuseColor = new Vector3f(1, 1, 0)
    float specularIntensity = 1f
    float specularPower = 8f

}
