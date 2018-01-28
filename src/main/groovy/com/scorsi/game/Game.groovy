package com.scorsi.game

import com.scorsi.engine.core.Engine
import com.scorsi.engine.core.IGame
import com.scorsi.engine.core.Input
import com.scorsi.engine.core.Time
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.Shader
import com.scorsi.engine.rendering.ShaderProgram
import com.scorsi.engine.rendering.Texture
import com.scorsi.engine.rendering.Transform
import com.scorsi.engine.rendering.Camera
import com.scorsi.engine.rendering.lights.Attenuation
import com.scorsi.engine.rendering.lights.DirectionalLight
import com.scorsi.engine.rendering.lights.PointLight
import com.scorsi.engine.rendering.lights.SpotLight
import com.scorsi.engine.core.math.Vector2f
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.core.math.Vertex
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static Transform.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Game implements IGame {

    private Engine engine

    private Mesh mesh
    private Material material
    private ShaderProgram shaderProgram
    private Transform transform
    private Camera camera
    private DirectionalLight directionalLight
    private PointLight[] pointLights = []
    private SpotLight[] spotLights = []
    private Vector3f ambientLight

    void initialize(Engine engine) {
        this.engine = engine

        material = new Material(Texture.loadTexture("test.png"), new Vector3f(1f, 1f, 1f), 1f, 8f)
        camera = new Camera()
        transform = new Transform()
        ambientLight = new Vector3f(0.01f, 0.01f, 0.01f)
        directionalLight = new DirectionalLight(new Vector3f(0.1f, 0.1f, 0.1f), 0.8f, new Vector3f(1f, 1f, 1f))
        pointLights = [new PointLight(new Vector3f(1f, 0.5f, 0f), 0.8f, new Vector3f(-2f, 0f, 5f), new Attenuation(0f, 0f, 1f), 6f),
                       new PointLight(new Vector3f(0f, 0.5f, 1f), 0.8f, new Vector3f(2f, 0f, 7f), new Attenuation(0f, 0f, 1f), 6f)]
        spotLights = [new SpotLight(new Vector3f(1f, 0.5f, 0f), 0.8f, new Vector3f(-2f, 0f, 5f), new Attenuation(0f, 0f, 0.1f), 30f, new Vector3f(1, 1, 1), 0.7f)]

        camera.position.z = -2

        /*
        Vertex[] vertices = [new Vertex(new Vector3f(-1.0f, -1.0f, 0.5773f), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(0.0f, -1.0f, -1.15475f), new Vector2f(0.5f, 0.0f)),
                             new Vertex(new Vector3f(1.0f, -1.0f, 0.5773f), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.5f, 1.0f))];

        int[] indices = [0, 3, 1,
                         1, 3, 2,
                         2, 3, 0,
                         1, 2, 0]
         */

        float fieldDepth = 10.0f
        float fieldWidth = 10.0f

        Vertex[] vertices = [new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth), new Vector2f(0.0f, 0.0f)),
                             new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3 as float), new Vector2f(0.0f, 1.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, -fieldDepth), new Vector2f(1.0f, 0.0f)),
                             new Vertex(new Vector3f(fieldWidth * 3f as float, 0.0f, fieldDepth * 3 as float), new Vector2f(1.0f, 1.0f))];

        int[] indices = [0, 1, 2,
                         2, 1, 3]

        mesh = new Mesh(vertices, indices, true)

        transform.camera = camera

        shaderProgram = new ShaderProgram()
                .attachShader(Shader.loadVertexShader("phong.vert"))
                .attachShader(Shader.loadFragmentShader("phong.frag"))
                .link()
                .addUniform("transformProjected", "transform", "baseColor", "ambientLight", "specularIntensity", "specularPower", "eyePos")
                .addDirectionalLightUniform("directionalLight")

        for (int i in pointLights.indices) {
            shaderProgram.addPointLightUniform("pointLights[" + i + "]")
        }

        for (int i in spotLights.indices) {
            shaderProgram.addSpotLightUniform("spotLights[" + i + "]")
        }

        shaderProgram.use()

        setProjection(70f, engine.window.width as float, engine.window.height as float, 0.1f, 1000f)
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

        transform.setTranslation(new Vector3f(0, -1, 5))
        //transform.setRotation(0, sinTemp * 180, 0);

        pointLights[0].setPosition(new Vector3f(3, 0, 8.0f * (Math.sin(temp) + 1.0 / 2.0) + 10f as float))
        pointLights[1].setPosition(new Vector3f(7, 0, 8.0f * (Math.cos(temp) + 1.0 / 2.0) + 10f as float))

        //transform.setScale(0.7f * sinTemp, 0.7f * sinTemp, 0.7f * sinTemp);

        spotLights[0].position = camera.position
        spotLights[0].direction = camera.forward
    }

    void render() {
        shaderProgram.use()
                .setUniform("transformProjected", transform.projectedTransformation)
                .setUniform("transform", transform.transformation)
                .setUniform("baseColor", material.color)
                .setUniform("ambientLight", ambientLight)
                .setUniform("directionalLight", directionalLight)
                .setUniform("specularIntensity", material.specularIntensity)
                .setUniform("specularPower", material.specularPower)
                .setUniform("eyePos", transform.camera.position)

        pointLights.eachWithIndex { pointLight, index ->
            shaderProgram.setUniform("pointLights[" + index + "]", pointLight)
        }
        spotLights.eachWithIndex { spotLight, index ->
            shaderProgram.setUniform("spotLights[" + index + "]", spotLight)
        }

        material.bind()
        mesh.draw()
    }

    void shutdown() {

    }

}
