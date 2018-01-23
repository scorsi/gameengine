package com.scorsi.gameengine

import com.scorsi.gameengine.graphics.*
import com.scorsi.gameengine.math.Vector2f
import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic

@CompileStatic
class Game {

    private Mesh mesh
    private Material material
    private ShaderProgram shaderProgram
    private Transform transform
    private Camera camera

    Game() {
        mesh = new Mesh()
        material = new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f))
        camera = new Camera()
        transform = new Transform()

        camera.position.z = -2

        Vertex[] vertices = [new Vertex(new Vector3f(-1, -1, 0), new Vector2f(0, 0)),
                             new Vertex(new Vector3f(0, 1, 0), new Vector2f(0.5f, 0)),
                             new Vertex(new Vector3f(1, -1, 0), new Vector2f(1.0f, 0)),
                             new Vertex(new Vector3f(0, -1, 1), new Vector2f(0.5f, 1.0f))]

        int[] indices = [3, 1, 0,
                         2, 1, 3,
                         0, 1, 2,
                         0, 2, 3]

        mesh.addVertices(vertices, indices)

        transform.camera = camera
        Transform.setProjection(70f, (float) MainComponent.WIDTH, (float) MainComponent.HEIGHT, 0.1f, 1000f)

        shaderProgram = new ShaderProgram()
                .attachShader(Shader.loadVertexShader("phongVertex.vert"))
                .attachShader(Shader.loadFragmentShader("phongVertex.frag"))
                .link()
                .use()
    }

    void input(Input input) {
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

    void update() {
    }

    void render() {
        shaderProgram.use()
                .setUniform("transform", transform.getProjectedTransformation())
                .setUniform("baseColor", material.color)
                .setUniform("ambientLight", new Vector3f(0.1f, 0.1f, 0.1f))
        material.bind()
        mesh.draw()
    }

}
