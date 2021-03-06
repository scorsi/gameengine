package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f
import org.lwjgl.system.MemoryUtil

import java.nio.FloatBuffer
import java.nio.IntBuffer

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL13.GL_TEXTURE0
import static org.lwjgl.opengl.GL13.glActiveTexture
import static org.lwjgl.opengl.GL15.*
import static org.lwjgl.opengl.GL20.*
import static org.lwjgl.opengl.GL30.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Mesh {

    private final int vaoId

    private final List<Integer> vboIdList

    private final int vertexCount

    Mesh(float[] positions, float[] textCoords, float[] normals, int[] indices) {
        FloatBuffer posBuffer = null
        FloatBuffer textCoordsBuffer = null
        FloatBuffer vecNormalsBuffer = null
        IntBuffer indicesBuffer = null
        try {
            vertexCount = indices.length
            vboIdList = new ArrayList()

            vaoId = glGenVertexArrays()
            glBindVertexArray(vaoId)

            // Position VBO
            int vboId = glGenBuffers()
            vboIdList.add(vboId)
            posBuffer = MemoryUtil.memAllocFloat(positions.length)
            posBuffer.put(positions).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, posBuffer, GL_STATIC_DRAW)
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)

            // Texture coordinates VBO
            vboId = glGenBuffers()
            vboIdList.add(vboId)
            textCoordsBuffer = MemoryUtil.memAllocFloat(textCoords.length)
            textCoordsBuffer.put(textCoords).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, textCoordsBuffer, GL_STATIC_DRAW)
            glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0)

            // Vertex normals VBO
            vboId = glGenBuffers()
            vboIdList.add(vboId)
            vecNormalsBuffer = MemoryUtil.memAllocFloat(normals.length)
            vecNormalsBuffer.put(normals).flip()
            glBindBuffer(GL_ARRAY_BUFFER, vboId)
            glBufferData(GL_ARRAY_BUFFER, vecNormalsBuffer, GL_STATIC_DRAW)
            glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0)

            // Index VBO
            vboId = glGenBuffers()
            vboIdList.add(vboId)
            indicesBuffer = MemoryUtil.memAllocInt(indices.length)
            indicesBuffer.put(indices).flip()
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboId)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW)

            glBindBuffer(GL_ARRAY_BUFFER, 0)
            glBindVertexArray(0)
        } finally {
            if (posBuffer != null) {
                MemoryUtil.memFree(posBuffer)
            }
            if (textCoordsBuffer != null) {
                MemoryUtil.memFree(textCoordsBuffer)
            }
            if (vecNormalsBuffer != null) {
                MemoryUtil.memFree(vecNormalsBuffer)
            }
            if (indicesBuffer != null) {
                MemoryUtil.memFree(indicesBuffer)
            }
        }
    }

    int getVaoId() {
        return vaoId
    }

    int getVertexCount() {
        return vertexCount
    }

    void render() {
        // Draw the mesh
        glBindVertexArray(getVaoId())
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glDrawElements(GL_TRIANGLES, getVertexCount(), GL_UNSIGNED_INT, 0)

        // Restore state
        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
        glBindVertexArray(0)
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    void cleanUp() {
        glDisableVertexAttribArray(0)

        // Delete the VBOs
        glBindBuffer(GL_ARRAY_BUFFER, 0)
        for (int vboId : vboIdList) {
            glDeleteBuffers(vboId)
        }

        // Delete the VAO
        glBindVertexArray(0)
        glDeleteVertexArrays(vaoId)
    }

}
