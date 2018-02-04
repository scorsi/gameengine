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

    GameObject testMesh1
    GameObject testMesh2

    void initialize() {
        def mainCamera = new GameObject().addComponent(new MovableCamera(70f, (engine.window.width / engine.window.height) as float, 0.1f, 1000f))

        float fieldDepth = 10.0f
        float fieldWidth = 10.0f

        Vertex[] vertices = [new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(-fieldWidth, 0.0f as float, fieldDepth * 3 as float), new Vector2f(0.0f, 1.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3 as float, 0.0f as float, -fieldDepth as float), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3 as float, 0.0f as float, fieldDepth * 3 as float), new Vector2f(1.0f, 1.0f))]

        int[] indices = [0, 1, 2,
                         2, 1, 3]

        Vertex[] vertices2 = [new Vertex(new Vector3f(-fieldWidth / 10 as float, 0.0f, -fieldDepth / 10 as float), new Vector2f(0.0f, 0.0f)),
                              new Vertex(new Vector3f(-fieldWidth / 10 as float, 0.0f, fieldDepth / 10 * 3 as float), new Vector2f(0.0f, 1.0f)),
                              new Vertex(new Vector3f(fieldWidth / 10 * 3 as float, 0.0f, -fieldDepth / 10 as float), new Vector2f(1.0f, 0.0f)),
                              new Vertex(new Vector3f(fieldWidth / 10 * 3 as float, 0.0f, fieldDepth / 10 * 3 as float), new Vector2f(1.0f, 1.0f))]

        int[] indices2 = [0, 1, 2,
                          2, 1, 3]

        def mesh2 = new Mesh(vertices2, indices2, true)
        def mesh = new Mesh(vertices, indices, true)
        def material = new Material()
        material.textures["diffuse"] = Texture.loadTexture("test.png")
        material.floats["specularIntensity"] = 1f
        material.floats["specularPower"] = 8f

//        Material material = new Material(Texture.loadTexture("test.png"), new Vector3f(1, 1, 1), 1, 8)

        def planeObject = new GameObject().addComponent(new MeshRenderer(mesh, material))
        planeObject.transform.translation.y = -1

        testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material))
        testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material))

        testMesh1.transform.translation.z = 5
//        testMesh1.transform.rotation = new Quaternion(new Vector3f(0, 1, 0), 30)

        testMesh2.transform.translation.z = 5
        testMesh2.transform.translation.y = 1
        testMesh2.transform.rotation = new Quaternion(new Vector3f(0, 1, 0), 90)

        def directionalLightObject = new GameObject()
                .addComponent(new DirectionalLight(new Vector3f(0, 0, 1), 0.4f, new Vector3f(1, 1, 1)))

        def pointLightObject = new GameObject()
                .addComponent(new PointLight(new Vector3f(0, 1, 0), 0.4f, new Vector3f(0, 0, 1)))

        def spotLightObject = new GameObject()
                .addComponent(new SpotLight(new Vector3f(0, 1, 1), 0.4f,
                new Vector3f(0, 0, 0.1f), 0.7f))

        spotLightObject.transform.translation.x = 5
        spotLightObject.transform.translation.z = 5

        spotLightObject.transform.rotation = new Quaternion(new Vector3f(0, 1, 0), -90.0f)

        addGameObjects(mainCamera, planeObject, /*testMesh1, */directionalLightObject, pointLightObject, spotLightObject)
        testMesh1.addChildren(testMesh2)


        def monkeyMesh = new Mesh("monkey3.obj")
        def monkeyObj = new GameObject().addComponent(new MeshRenderer(monkeyMesh, material))

        monkeyObj.transform.translation = new Vector3f(5, 5, 5)
        monkeyObj.transform.rotate(new Vector3f(0, 1, 0), -70f)

        addGameObjects(monkeyObj)
    }

    float angle = 0

    @Override
    void update(float delta) {
        angle += (delta / 5) as float
        if (angle > 360) angle = 0
        testMesh1.transform.rotate(new Vector3f(0, 1, 0), angle)
    }
}
