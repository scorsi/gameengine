package com.scorsi.engine.rendering.camera

import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Camera extends GameObject {

    static final Vector3f yAxis = new Vector3f(0f, 1f, 0f)

    Vector3f position
    Vector3f forward
    Vector3f up
    Matrix4f projection

    Camera(float fov, float aspect, float zNear, float zFar) {
        this.position = new Vector3f(0, 0, 0)
        this.forward = new Vector3f(0, 0, 1).normalize()
        this.up = new Vector3f(0, 1, 0).normalize()
        this.projection = Matrix4f.perspective(fov, aspect, zNear, zFar)
    }

    Matrix4f getViewProjection() {
        def cameraRotationMatrix = new Matrix4f().initRotation(forward, up)
        def cameraTranslationMatrix = Matrix4f.translate(-position.x, -position.y, -position.z)

        return projection * cameraRotationMatrix * cameraTranslationMatrix
    }

    void move(Vector3f direction, float amount) {
        position = position + direction.scale(amount)
    }

    void rotateY(float angle) {
        forward = forward.rotate(angle, yAxis).normalize()
        up = forward.cross(yAxis.cross(forward).normalize()).normalize()
    }

    void rotateX(float angle) {
        Vector3f hAxis = yAxis.cross(forward).normalize()
        forward = forward.rotate(angle, hAxis).normalize()
        up = forward.cross(hAxis).normalize()
    }

    Vector3f getLeft() {
        return up.cross(forward).normalize()
    }

    Vector3f getRight() {
        return forward.cross(up).normalize()
    }

}
