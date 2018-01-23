package com.scorsi.gameengine;

import com.scorsi.gameengine.math.Vector3f;

public class Game {

    private Mesh mesh;
    private ShaderProgram shaderProgram;
    private Transform transform;

    public Game() {
        mesh = ResourceLoader.loadMesh("cube.obj");

        transform = new Transform();

        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(Shader.loadVertexShader("basicVertex.vert"));
        shaderProgram.attachShader(Shader.loadFragmentShader("basicVertex.frag"));
        shaderProgram.link();
        shaderProgram.use();
    }

    public void input(Input input) {
    }

    float tmp = 0f;

    public void update() {
        tmp += Time.getDelta();

        float sinTmp = (float) Math.sin(tmp);

        //transform.setTranslation(sinTmp, 0, 0);
        transform.setRotation(0, sinTmp * 180, 0);
        transform.setScale(0.5f, 0.5f, 0.5f);
    }

    public void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }

}
