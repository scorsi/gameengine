package com.scorsi.gameengine

import com.scorsi.gameengine.graphics.Mesh
import com.scorsi.gameengine.math.Vector3f
import com.scorsi.gameengine.math.Vertex

class ResourceLoader {

    static String loadShader(String fileName) {
        StringBuilder shaderSource = new StringBuilder()
        BufferedReader shaderReader = null

        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName))
            String line

            while ((line = shaderReader.readLine()) != null) {
                shaderSource.append(line).append("\n")
            }

            shaderReader.close()
        } catch (Exception e) {
            e.printStackTrace()
            System.exit(1)
        }


        return shaderSource.toString()
    }

    static Mesh loadMesh(String fileName) {
        String[] splitArray = fileName.split("\\.")
        String ext = splitArray[splitArray.length - 1]

        if (ext != "obj") {
            System.err.println("Error: File format not supported for mesh data: " + ext)
            new Exception().printStackTrace()
            System.exit(1)
        }

        ArrayList<Vertex> vertices = new ArrayList<>()
        ArrayList<Integer> indices = new ArrayList<>()

        BufferedReader meshReader

        try {
            meshReader = new BufferedReader(new FileReader("./res/models/" + fileName))
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

            meshReader.close()

            Mesh res = new Mesh()
            Vertex[] vertexData = new Vertex[vertices.size()]
            vertices.toArray(vertexData)

            Integer[] indexData = new Integer[indices.size()]
            indices.toArray(indexData)

            res.addVertices(vertexData, Util.toIntArray(indexData))

            return res
        } catch (Exception e) {
            e.printStackTrace()
            System.exit(1)
        }

        return null
    }

}
