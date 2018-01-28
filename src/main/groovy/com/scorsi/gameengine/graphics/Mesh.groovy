package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.Util
import com.scorsi.gameengine.math.Vector3f
import com.scorsi.gameengine.math.Vertex
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

    private Mesh() {
        initMeshData()
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
        glBufferData(GL_ARRAY_BUFFER, Util.createFlippedBuffer(vertices), GL_STATIC_DRAW)

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo)
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, Util.createFlippedBuffer(indices), GL_STATIC_DRAW)

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

        vertices.each { Vertex vertex ->
            vertex.normal = vertex.normal.normalize()
        }
    }

    static Mesh loadMesh(String fileName) {
        def splitArray = fileName.split("\\.")
        def ext = splitArray[splitArray.length - 1]

        if (ext != "obj") {
            System.err.println("Error: File format not supported for mesh data: " + ext)
            new Exception().printStackTrace()
            System.exit(1)
        }

        def vertices = new ArrayList<Vertex>()
        def indices = new ArrayList<Integer>()

        try {
            new BufferedReader(new FileReader("./res/models/" + fileName)).withCloseable { meshReader ->
                String line

                while ((line = meshReader.readLine()) != null) {
                    String[] tokens = line.split(" ")
                    tokens = Util.removeEmptyStrings(tokens)

                    if (tokens.length == 0 || tokens[0] == "#")
                        continue
                    else if (tokens[0] == "v") {
                        vertices.add(new Vertex(new Vector3f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2]),
                                Float.valueOf(tokens[3]))))
                    } else if (tokens[0] == "f") {
                        indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1)
                        indices.add(Integer.parseInt(tokens[2].split("/")[0]) - 1)
                        indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1)

                        if (tokens.length > 4) {
                            indices.add(Integer.parseInt(tokens[1].split("/")[0]) - 1)
                            indices.add(Integer.parseInt(tokens[3].split("/")[0]) - 1)
                            indices.add(Integer.parseInt(tokens[4].split("/")[0]) - 1)
                        }
                    }
                }
            }

            Mesh res = new Mesh()
            Vertex[] vertexData = new Vertex[vertices.size()]
            vertices.toArray(vertexData)

            Integer[] indexData = new Integer[indices.size()]
            indices.toArray(indexData)

            res.addVertices(vertexData, Util.toIntArray(indexData), true)

            return res
        }
        catch (Exception e) {
            e.printStackTrace()
            System.exit(1)
        }

        return null
    }

}
