package com.scorsi.game

import com.scorsi.engine.components.*
import com.scorsi.engine.core.Game
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.core.math.Quaternion
import com.scorsi.engine.core.math.Vector2f
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.core.math.Vertex
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.Texture
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class TestGame extends Game {

    void initialize() {
        def mainCamera = new GameObject().addComponent(new MovableCamera(70f, (engine.window.width / engine.window.height) as float, 0.1f, 1000f))

        float fieldDepth = 10.0f
        float fieldWidth = 10.0f
        Vertex[] vertices = [new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3 as float), new Vector2f(0.0f, 1.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, fieldDepth * 3 as float), new Vector2f(1.0f, 1.0f))]
        int[] indices = [0, 1, 2,
                         2, 1, 3]

        def planeObject = new GameObject()
                .addComponent(new MeshRenderer(new Mesh(vertices, indices, true),
                new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f), 1f, 8f)))
        planeObject.transform.translation.y = -1

        def directionalLightObject = new GameObject()
                .addComponent(new DirectionalLight(new Vector3f(0, 0, 1), 0.4f, new Vector3f(1, 1, 1)))

        def pointLightObject = new GameObject()
                .addComponent(new PointLight(new Vector3f(0, 1, 0), 0.4f, new Vector3f(0, 0, 1)))

        def spotLightObject = new GameObject()
                .addComponent(new SpotLight(new Vector3f(0, 1, 1), 0.4f,
                new Vector3f(0, 0, 0.1f), 0.7f))

        spotLightObject.transform.translation.x = 5
        spotLightObject.transform.translation.z = 5

        spotLightObject.transform.rotation = new Quaternion().initRotation(new Vector3f(0, 1, 0), -90.0f)

        root.addChildren(mainCamera, planeObject, directionalLightObject, pointLightObject, spotLightObject)
    }

}
