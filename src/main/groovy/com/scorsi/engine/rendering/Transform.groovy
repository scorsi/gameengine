package com.scorsi.engine.rendering

import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.camera.Camera
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Transform {

    Vector3f translation
    Vector3f rotation
    Vector3f scale

    Transform() {
        translation = new Vector3f()
        rotation = new Vector3f()
        scale = new Vector3f(1f, 1f, 1f)
    }

    Matrix4f getTransformation() {
        def translationMatrix = Matrix4f.translate(translation.x, translation.y, translation.z)
        def rotationMatrix = new Matrix4f().initRotation(rotation.x, rotation.y, rotation.z)
        def scaleMatrix = Matrix4f.scale(scale.x, scale.y, scale.z)

        return translationMatrix * rotationMatrix * scaleMatrix
    }

    Matrix4f getProjectedTransformation(Camera camera) {
        return camera.viewProjection * transformation
    }

}
