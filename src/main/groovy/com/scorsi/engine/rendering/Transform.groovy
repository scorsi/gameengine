package com.scorsi.engine.rendering

import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.core.math.Quaternion
import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Transform {

    Vector3f translation
    Quaternion rotation
    Vector3f scale

    Transform() {
        translation = new Vector3f()
        rotation = new Quaternion()
        scale = new Vector3f(1f, 1f, 1f)
    }

    Matrix4f getTransformation() {
        def translationMatrix = Matrix4f.translate(translation.x, translation.y, translation.z)
        def rotationMatrix = rotation.toRotationMatrix()
        def scaleMatrix = Matrix4f.scale(scale.x, scale.y, scale.z)

        return translationMatrix * rotationMatrix * scaleMatrix
    }

}
