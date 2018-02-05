package com.scorsi.engine.components

import com.scorsi.engine.core.Input
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.glfw.GLFW.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class MovableCamera extends Camera {

    static final float MOUSE_SENSITIVITY = 0.5f

    @Override
    void input(float delta, Input input) {
        def moveDist = CAMERA_POS_STEP * delta as float
        if (input.isKeyPressed(GLFW_KEY_W))
            cameraInc.z -= moveDist
        if (input.isKeyPressed(GLFW_KEY_S))
            cameraInc.z += moveDist
        if (input.isKeyPressed(GLFW_KEY_A))
            cameraInc.x -= moveDist
        if (input.isKeyPressed(GLFW_KEY_D))
            cameraInc.x += moveDist
        if (input.isKeyPressed(GLFW_KEY_Z))
            cameraInc.y += moveDist
        if (input.isKeyPressed(GLFW_KEY_X))
            cameraInc.y -= moveDist
    }

}
