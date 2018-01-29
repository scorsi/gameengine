package com.scorsi.engine.components

import com.scorsi.engine.core.GameComponent
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Camera extends GameComponent {

    static final Vector3f yAxis = new Vector3f(0f, 1f, 0f)

    Matrix4f projection

    Camera(float fov, float aspect, float zNear, float zFar) {
        this.projection = Matrix4f.perspective(fov, aspect, zNear, zFar)
    }

    Matrix4f getViewProjection() {
        def cameraRotation = parent.transform.rotation.toRotationMatrix()
        def cameraTranslation = Matrix4f.translate(-parent.transform.translation.x, -parent.transform.translation.y, -parent.transform.translation.z)

        return projection * cameraRotation * cameraTranslation
    }

    void move(Vector3f direction, float amount) {
        parent.transform.translation = parent.transform.translation + direction * amount
    }

    void addToRenderingEngine(RenderingEngine renderingEngine) {
        renderingEngine.mainCamera = this
    }

}
