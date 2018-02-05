package com.scorsi.engine.rendering

import com.scorsi.engine.core.Utils
import com.scorsi.engine.core.math.Vertex
import com.scorsi.engine.rendering.meshLoading.ObjModel
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL15.*
import static org.lwjgl.opengl.GL20.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Mesh {

    private int vbo
    private int ibo
    private int size

    Mesh(String filename) {
        initMeshData()
        loadMesh(filename)
    }

    Mesh(Vertex[] vertices, int[] indices, boolean calcNormals = false) {
        initMeshData()
        addVertices(vertices, indices, calcNormals)
    }

    private void initMeshData() {
        vbo = glGenBuffers()
        ibo = glGenBuffers()
        size = 0
    }

    private void addVertices(Vertex[] vertices, int[] indices, boolean doCalcNormals = false) {
        if (doCalcNormals) {
            calcNormals(vertices, indices)
        }

        size = indices.length

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glBufferData(GL_ARRAY_BUFFER, Utils.createFlippedBuffer(vertices), GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer(indices), GL_STATIC_DRAW)

    }

    void draw() {
        glEnableVertexAttribArray(0)
        glEnableVertexAttribArray(1)
        glEnableVertexAttribArray(2)

        glBindBuffer(GL_ARRAY_BUFFER, vbo)
        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.SIZE * 4, 0)
        glVertexAttribPointer(1, 2, GL_FLOAT, false, Vertex.SIZE * 4, 12)
        glVertexAttribPointer(2, 3, GL_FLOAT, false, Vertex.SIZE * 4, 20)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glDrawElements(GL_TRIANGLES, size, GL_UNSIGNED_INT, 0)

        glDisableVertexAttribArray(0)
        glDisableVertexAttribArray(1)
        glDisableVertexAttribArray(2)
    }

    private static void calcNormals(Vertex[] vertices, int[] indices) {
        for (int i = 0; i < indices.length; i += 3) {
            int i0 = indices[i]
            int i1 = indices[i + 1]
            int i2 = indices[i + 2]

            def v1 = vertices[i1].position - vertices[i0].position
            def v2 = vertices[i2].position - vertices[i0].position

            def normal = v1.cross(v2).normalize()
            vertices[i0].normal = vertices[i0].normal + normal
            vertices[i1].normal = vertices[i1].normal + normal
            vertices[i2].normal = vertices[i2].normal + normal
        }

        vertices.each { vertex ->
            vertex.normal = vertex.normal.normalize()
        }
    }

    private void loadMesh(String filename) {
        def objModel = new ObjModel(filename)
        def model = objModel.toIndexedModel()
//        model.calcNormals()

        def vertices = new ArrayList<Vertex>()

        for (i in 0..model.positions.size() - 1) {
            vertices.add(new Vertex(model.positions[i],
                    model.texturePosition[i],
                    model.normals[i]))
        }

        def vertexData = vertices.toArray() as Vertex[]
        def indexData = model.indices.toArray() as int[]

        addVertices(vertexData, indexData, false)
    }

}
