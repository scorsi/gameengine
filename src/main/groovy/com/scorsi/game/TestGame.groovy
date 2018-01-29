package com.scorsi.game

import com.scorsi.engine.core.*
import com.scorsi.engine.core.math.Vector2f
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.core.math.Vertex
import com.scorsi.engine.rendering.*
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class TestGame extends Game {

    private Camera camera

    void initialize() {
        camera = new Camera()
        camera.position.z = -2
        Transform.camera = camera
        Transform.setProjection(70f, engine.window.width as float, engine.window.height as float, 0.1f, 1000f)

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
        planeObject.transform.translation.z = 5
        planeObject.components.add(new MeshRenderer(planeObject,
                new Mesh(vertices, indices, true),
                new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f), 1f, 8f)))

        root.children.add(planeObject)
    }

    void input(Input input) {
        super.input(input)

        final def moveAmount = (10f * Time.getDelta()) as float
        final def rotateAmount = (100f * Time.getDelta()) as float

        if (input.isKeyDownRepeated(Input.KEY_W)) {
            camera.move(camera.forward, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_S)) {
            camera.move(camera.forward, -moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_A)) {
            camera.move(camera.right, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_D)) {
            camera.move(camera.left, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_UP)) {
            camera.rotateX(-rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_DOWN)) {
            camera.rotateX(rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_LEFT)) {
            camera.rotateY(-rotateAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_RIGHT)) {
            camera.rotateY(rotateAmount)
        }
    }

}
