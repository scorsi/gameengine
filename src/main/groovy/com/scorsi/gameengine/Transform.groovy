package com.scorsi.gameengine

import com.scorsi.gameengine.math.Matrix4f
import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic

@CompileStatic
class Transform {

    private Camera camera

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
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.x, translation.y, translation.z)
        Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.x, rotation.y, rotation.z)
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.x, scale.y, scale.z)

        return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix))
    }

    Matrix4f getProjectedTransformation() {
        Matrix4f transformationMatrix = getTransformation()
        Matrix4f projectionMatrix = new Matrix4f().initProjection(fov, width, height, zNear, zFar)
        Matrix4f cameraRotationMatrix = new Matrix4f().initCamera(camera.forward, camera.up)
        Matrix4f cameraTranslationMatrix = new Matrix4f().initTranslation(-camera.position.x, -camera.position.y, -camera.position.z)

        return projectionMatrix.multiply(cameraRotationMatrix.multiply(cameraTranslationMatrix.multiply(transformationMatrix)))
    }

    static void setProjection(float fov, float width, float height, float zNear, float zFar) {
        Transform.fov = fov
        Transform.width = width
        Transform.height = height
        Transform.zNear = zNear
        Transform.zFar = zFar
    }

    Vector3f getTranslation() {
        return translation
    }

    void setTranslation(Vector3f translation) {
        this.translation = translation
    }

    void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z)
    }

    Vector3f getRotation() {
        return rotation
    }

    void setRotation(Vector3f rotation) {
        this.rotation = rotation
    }

    void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z)
    }

    Vector3f getScale() {
        return scale
    }

    void setScale(Vector3f scale) {
        this.scale = scale
    }

    void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z)
    }

    Camera getCamera() {
        return camera
    }

    void setCamera(Camera camera) {
        this.camera = camera
    }

}
