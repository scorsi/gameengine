package com.scorsi.gameengine

import com.scorsi.gameengine.math.Matrix4f
import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic

@CompileStatic
class Transform {

    Camera camera

    private static float zNear
    private static float zFar
    private static float width
    private static float height
    private static float fov

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

    Matrix4f getProjectedTransformation() {
        def transformationMatrix = getTransformation()
        def projectionMatrix = Matrix4f.perspective(fov, (width / height) as float, zNear, zFar)
        def cameraRotationMatrix = new Matrix4f().initCamera(camera.forward, camera.up)
        def cameraTranslationMatrix = Matrix4f.translate(-camera.position.x, -camera.position.y, -camera.position.z)

        return projectionMatrix * cameraRotationMatrix * cameraTranslationMatrix * transformationMatrix
    }

    static void setProjection(float fov, float width, float height, float zNear, float zFar) {
        Transform.fov = fov
        Transform.width = width
        Transform.height = height
        Transform.zNear = zNear
        Transform.zFar = zFar
    }

}
