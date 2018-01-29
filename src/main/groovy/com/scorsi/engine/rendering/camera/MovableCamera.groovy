package com.scorsi.engine.rendering.camera

import com.scorsi.engine.core.Input
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class MovableCamera extends Camera {

    MovableCamera(float fov, float aspect, float zNear, float zFar) {
        super(fov, aspect, zNear, zFar)
    }

    void input(float delta, Input input) {
        final def moveAmount = (10f * delta) as float
        final def rotateAmount = (100f * delta) as float

        if (input.isKeyDownRepeated(Input.KEY_W)) {
            move(forward, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_S)) {
            move(forward, -moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_A)) {
            move(right, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_D)) {
            move(left, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_UP)) {
            rotateX(-rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_DOWN)) {
            rotateX(rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_LEFT)) {
            rotateY(-rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_RIGHT)) {
            rotateY(rotateAmount)
        }
    }

}
