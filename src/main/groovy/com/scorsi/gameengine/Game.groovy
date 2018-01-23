package com.scorsi.gameengine

import com.scorsi.gameengine.graphics.*
import com.scorsi.gameengine.graphics.lights.DirectionalLight
import com.scorsi.gameengine.math.Vector2f
import com.scorsi.gameengine.math.Vector3f
import com.scorsi.gameengine.math.Vertex
import groovy.transform.CompileStatic

import static com.scorsi.gameengine.Transform.*

@CompileStatic
class Game {

    private Mesh mesh
    private Material material
    private ShaderProgram shaderProgram
    private Transform transform
    private Camera camera
    private DirectionalLight directionalLight
    private Vector3f ambientLight

    Game() {
        mesh = new Mesh()
        material = new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f))
        camera = new Camera()
        transform = new Transform()
        ambientLight = new Vector3f(0.1f, 0.1f, 0.1f)
        directionalLight = new DirectionalLight(new Vector3f(1, 1, 1), 0.8f, new Vector3f(1, 1, 1))

        camera.position.z = -2

        Vertex[] vertices = [new Vertex(new Vector3f(-1.0f, -1.0f, 0.5773f), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f, 0.0f)),
                             new Vertex(new Vector3f(1.0f, -1.0f, 0.5773f), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f, 1.0f))];

        int[] indices = [0, 3, 1,
                         1, 3, 2,
                         2, 3, 0,
                         1, 2, 0]

        mesh.addVertices(vertices, indices, true)

        transform.camera = camera

        shaderProgram = new ShaderProgram()
                .attachShader(Shader.loadVertexShader("phong.vert"))
                .attachShader(Shader.loadFragmentShader("phong.frag"))
                .link()
                .addUniform("transformProjected", "transform", "baseColor", "ambientLight")
                .addDirectionalLigthUniform("directionalLight")
                .use()

        setProjection(70f, MainComponent.WIDTH as float, MainComponent.HEIGHT as float, 0.1f, 1000f)
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

    float temp = 0.0f

    void update() {
        temp += Time.getDelta() as float

        def sinTemp = Math.sin(temp) as float

        transform.translation = new Vector3f(0f, 0f, 5f)
        transform.rotation = new Vector3f(0f, (sinTemp * 180f) as float, 0f)
        //transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);
    }

    void render() {
        shaderProgram.use()
                .setUniform("transformProjected", transform.projectedTransformation)
                .setUniform("transform", transform.transformation)
                .setUniform("baseColor", material.color)
                .setUniform("ambientLight", ambientLight)
                .setUniform("directionalLight", directionalLight)
        material.bind()
        mesh.draw()
    }

}
