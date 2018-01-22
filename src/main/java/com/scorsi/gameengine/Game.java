package com.scorsi.gameengine;

import com.scorsi.gameengine.math.Vector3f;

public class Game {

    private Mesh mesh;
    private ShaderProgram shaderProgram;
    private Transform transform;

    public Game() {
        mesh = new Mesh();
        Vertex[] data = new Vertex[]{new Vertex(new Vector3f(-1f, -1f, 0f)),
                new Vertex(new Vector3f(0f, 1f, 0f)),
                new Vertex(new Vector3f(1f, -1f, 0f))};
        mesh.addVertices(data);

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

        transform.setTranslation(sinTmp, 0, 0);
        transform.setRotation(0, 0, sinTmp * 180);
        transform.setScale(sinTmp, sinTmp, sinTmp);
    }

    public void render() {
        shaderProgram.use();
        shaderProgram.setUniform("transform", transform.getTransformation());
        mesh.draw();
    }

}
