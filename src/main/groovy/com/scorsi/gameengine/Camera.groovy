package com.scorsi.gameengine

import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Camera {

    static final Vector3f yAxis = new Vector3f(0f, 1f, 0f)

    Vector3f position
    Vector3f forward
    Vector3f up

    Camera() {
        this(new Vector3f(0f, 0f, 0f), new Vector3f(0f, 0f, 1f), new Vector3f(0f, 1f, 0f))
    }

    Camera(Vector3f position, Vector3f forward, Vector3f up) {
        this.position = position
        this.forward = forward
        this.up = up

        this.forward = forward.normalize()
        this.up = up.normalize()
    }

    void move(Vector3f direction, float amount) {
        position = position.plus(direction.scale(amount))
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
