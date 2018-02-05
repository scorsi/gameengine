package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Camera extends GameComponent {

    static final float CAMERA_POS_STEP = 1f

    protected Vector3f cameraInc = new Vector3f()

    @Override
    void update(float delta) {
        movePosition(cameraInc.x, cameraInc.y, cameraInc.z)
        cameraInc.x = 0
        cameraInc.y = 0
        cameraInc.z = 0
    }

    void movePosition(float offsetX, float offsetY, float offsetZ) {
        if (offsetZ != 0) {
            object.transform.position.x += Math.sin(Math.toRadians(object.transform.rotation.y)) * -1.0f * offsetZ as float
            object.transform.position.z += Math.cos(Math.toRadians(object.transform.rotation.y)) * offsetZ as float
        }
        if (offsetX != 0) {
            object.transform.position.x += Math.sin(Math.toRadians(object.transform.rotation.y - 90)) * -1.0f * offsetX as float
            object.transform.position.z += Math.cos(Math.toRadians(object.transform.rotation.y - 90)) * offsetX as float
        }
        object.transform.position.y += offsetY
    }

    void moveRotation(float offsetX, float offsetY, float offsetZ) {
        object.transform.rotation.x += offsetX
        object.transform.rotation.y += offsetY
        object.transform.rotation.z += offsetZ
    }

}
