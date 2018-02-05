package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Matrix4f
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Transformation {

    private Matrix4f projectionMatrix = new Matrix4f()
    private Matrix4f modelViewMatrix = new Matrix4f()
    private Matrix4f viewMatrix = new Matrix4f()

    final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRatio = width / height as float
        projectionMatrix.identity()
                .perspective(fov, aspectRatio, zNear, zFar)
        return projectionMatrix
    }

    Matrix4f getViewMatrix(Transform transform) {
        Vector3f cameraPos = transform.getPosition()
        Vector3f rotation = transform.getRotation()
        viewMatrix.identity()
                .rotate(Math.toRadians(rotation.x) as float, new Vector3f(1, 0, 0))
                .rotate(Math.toRadians(rotation.y) as float, new Vector3f(0, 1, 0))
                .translate(-cameraPos.x, -cameraPos.y, -cameraPos.z)
        return viewMatrix
    }

    Matrix4f getModelViewMatrix(Transform transform, Matrix4f viewMatrix) {
        Vector3f rotation = transform.getRotation()
        modelViewMatrix.identity()
                .translate(transform.getPosition())
                .rotateX(Math.toRadians(-rotation.x) as float)
                .rotateY(Math.toRadians(-rotation.y) as float)
                .rotateZ(Math.toRadians(-rotation.z) as float)
                .scale(transform.getScale())
        def viewCurr = new Matrix4f(viewMatrix)
        return viewCurr.mul(modelViewMatrix)
    }

}
