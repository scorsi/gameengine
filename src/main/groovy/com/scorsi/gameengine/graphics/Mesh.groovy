package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.Util
import com.scorsi.gameengine.Vertex
import groovy.transform.CompileStatic
import org.lwjgl.opengl.GL15

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL15.*
import static org.lwjgl.opengl.GL20.*

@CompileStatic
class Mesh {

    private int vbo
    private int ibo
    private int size

    Mesh() {
        vbo = glGenBuffers()
        ibo = glGenBuffers()
        size = 0
    }

    void addVertices(Vertex[] vertices, int[] indices) {
        size = indices.length

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        GL15.glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW)

    }

    void draw() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
    }

}
