package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Transform {

    Transform parent

    Vector3f position = new Vector3f()
    Vector3f rotation = new Vector3f()
    Vector3f scale = new Vector3f(1, 1, 1)

}
