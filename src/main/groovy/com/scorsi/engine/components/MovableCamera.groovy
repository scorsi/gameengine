package com.scorsi.engine.components

import com.scorsi.engine.core.Input
import com.scorsi.engine.core.math.Quaternion
import com.scorsi.engine.core.math.Vector2f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class MovableCamera extends Camera {

    boolean mouseLocked = false
    Vector2f centerPosition = new Vector2f(400, 300)

    MovableCamera(float fov, float aspect, float zNear, float zFar) {
        super(fov, aspect, zNear, zFar)
    }

    void input(float delta, Input input) {
        float sensitivity = -0.25f
        float movAmt = (float) (10 * delta)

        if (input.isKeyDownRepeated(Input.KEY_ENTER)) {
            input.setCursor(true)
            mouseLocked = false
        }
        if (input.isMouseDownRepeated(Input.MOUSE_BUTTON_1)) {
            input.setMousePosition(centerPosition)
            input.setCursor(false)
            mouseLocked = true
        }

        if (input.isKeyDownRepeated(Input.KEY_W))
            move(parent.transform.rotation.forward, movAmt)
        if (input.isKeyDownRepeated(Input.KEY_S))
            move(parent.transform.rotation.forward, -movAmt)
        if (input.isKeyDownRepeated(Input.KEY_A))
            move(parent.transform.rotation.right, -movAmt)
        if (input.isKeyDownRepeated(Input.KEY_D))
            move(parent.transform.rotation.right, movAmt)

        if (mouseLocked) {
            Vector2f deltaPos = input.getMousePosition() - centerPosition

            boolean rotY = deltaPos.getX() != 0
            boolean rotX = deltaPos.getY() != 0

            if (rotY)
                parent.transform.rotation = (parent.transform.rotation * new Quaternion().initRotation(yAxis, deltaPos.x * sensitivity as float)).normalize()
            if (rotX)
                parent.transform.rotation = (parent.transform.rotation * new Quaternion().initRotation(parent.transform.rotation.right, deltaPos.y * sensitivity as float)).normalize()

            if (rotY || rotX)
                input.setMousePosition(centerPosition)
        }
    }

}
