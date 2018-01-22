package com.scorsi.gameengine;

import com.scorsi.gameengine.math.Vector3f;

public class Game {

    private Mesh mesh;
    private ShaderProgram shaderProgram;

    public Game() {
        mesh = new Mesh();
        Vertex[] data = new Vertex[]{new Vertex(new Vector3f(-1f, -1f, 0f)),
                new Vertex(new Vector3f(0f, 1f, 0f)),
                new Vertex(new Vector3f(1f, -1f, 0f))};
        mesh.addVertices(data);

        shaderProgram = new ShaderProgram();
        shaderProgram.attachShader(Shader.loadVertexShader("basicVertex.vert"));
        shaderProgram.attachShader(Shader.loadFragmentShader("basicVertex.frag"));
        shaderProgram.bindFragmentDataLocation(0, "fragColor");
        shaderProgram.link();
        shaderProgram.use();
    }

    public void input(Input input) {
    }

    float tmp = 0f;

    public void update() {
        tmp += Time.getDelta();
        shaderProgram.setUniform("uniformFloat", (float) Math.abs(Math.sin(tmp)));
    }

    public void render() {
        shaderProgram.use();
        mesh.draw();
    }

}
