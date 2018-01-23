package com.scorsi.gameengine

import com.scorsi.gameengine.graphics.Material
import com.scorsi.gameengine.graphics.Mesh
import com.scorsi.gameengine.graphics.Shader
import com.scorsi.gameengine.graphics.ShaderProgram
import com.scorsi.gameengine.graphics.Texture
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

        transform = new Transform()
        transform.setCamera(camera)
        Transform.setProjection(70f, (float) MainComponent.WIDTH, (float) MainComponent.HEIGHT, 0.1f, 1000f)

        shaderProgram = new ShaderProgram()
        shaderProgram.attachShader(Shader.loadVertexShader("basicVertex.vert"))
        shaderProgram.attachShader(Shader.loadFragmentShader("basicVertex.frag"))
        shaderProgram.link()
        shaderProgram.use()
    }

    void input(Input input) {
        final float moveAmount = (float) (10f * Time.getDelta())
        final float rotateAmount = (float) (100f * Time.getDelta())

        if (input.isKeyDownRepeated(Input.KEY_W)) {
            camera.move(camera.forward, moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_S)) {
            camera.move(camera.forward, -moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_A)) {
            camera.move(camera.getRight(), moveAmount)
        }
        if (input.isKeyDownRepeated(Input.KEY_D)) {
            camera.move(camera.getLeft(), moveAmount)
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
        shaderProgram.setUniform("transform", transform.getProjectedTransformation())
        shaderProgram.setUniform("color", material.color)
        material.bind()
        mesh.draw()
    }

}
