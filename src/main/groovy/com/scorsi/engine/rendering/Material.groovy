package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector2f
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Material {

    HashMap<String, Texture> textures = new HashMap<>()
    HashMap<String, Vector3f> vectors = new HashMap<>()
    HashMap<String, Float> floats = new HashMap<>()

    Texture getSafeTexture(String name) {
        def r = textures[name]
        if (r == null)
            return new Texture("test.png")
        return r
    }

    Texture getTexture(String name) {
        textures[name]
    }

    Vector3f getSafeAttributeVector(String name) {
        def r = vectors[name]
        if (r == null)
            return new Vector3f()
        return r
    }

    Vector3f getAttributeVector(String name) {
        vectors[name]
    }

    Float getSafeAttributeFloat(String name) {
        def r = floats[name]
        if (r == null)
            return 0f
        return r
    }

    Float getAttributeFloat(String name) {
        floats[name]
    }

}
