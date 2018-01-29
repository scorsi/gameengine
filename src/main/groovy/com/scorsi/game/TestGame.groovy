package com.scorsi.game

import com.scorsi.engine.components.DirectionalLight
import com.scorsi.engine.components.MeshRenderer
import com.scorsi.engine.components.PointLight
import com.scorsi.engine.core.*
import com.scorsi.engine.core.math.Vector2f
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.core.math.Vertex
import com.scorsi.engine.rendering.*
import com.scorsi.engine.rendering.camera.MovableCamera
import com.scorsi.engine.rendering.lights.Attenuation
import com.scorsi.engine.rendering.lights.BaseLight
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class TestGame extends Game {

    void initialize() {
        def mainCamera = new MovableCamera(70f, (engine.window.width / engine.window.height) as float, 0.1f, 1000f)
        root.children.add(mainCamera)
        engine.renderingEngine.mainCamera = mainCamera
        def planeObject = new GameObject()

        float fieldDepth = 10.0f
        float fieldWidth = 10.0f
        Vertex[] vertices = [new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3 as float), new Vector2f(0.0f, 1.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, fieldDepth * 3 as float), new Vector2f(1.0f, 1.0f))]
        int[] indices = [0, 1, 2,
                         2, 1, 3]

        planeObject.transform.translation.y = -1
        planeObject.addComponent(new MeshRenderer(planeObject,
                new Mesh(vertices, indices, true),
                new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f), 1f, 8f)))

        def directionalLightObject = new GameObject()
        directionalLightObject.addComponent(new DirectionalLight(directionalLightObject, new BaseLight(new Vector3f(0, 0, 1), 0.4f), new Vector3f(1, 1, 1)))

        def pointLightObject = new GameObject()
        pointLightObject.addComponent(new PointLight(pointLightObject, new BaseLight(new Vector3f(0, 1, 0), 0.4f), new Vector3f(5, 0, 5), new Attenuation(0, 0, 1), 100))

        root.addChildren(planeObject, directionalLightObject, pointLightObject)
    }

}
