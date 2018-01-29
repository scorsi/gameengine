package com.scorsi.engine.rendering

import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Material {

    HashMap<String, Texture> textures = new HashMap<>()
    HashMap<String, Vector3f> vectors = new HashMap<>()
    HashMap<String, Float> floats = new HashMap<>()

    Texture getSafeTexture(String name) {
        def r = textures[name]
        if (r == null)
            return Texture.loadTexture("test.png")
        return r
    }

    Vector3f getSafeVector(String name) {
        def r = vectors[name]
        if (r == null)
            return new Vector3f()
        return r
    }

    float getSafeFloat(String name) {
        def r = floats[name]
        if (r == null)
            return 0f
        return r
    }

}
