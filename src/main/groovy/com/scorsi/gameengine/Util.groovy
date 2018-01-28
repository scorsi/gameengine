package com.scorsi.gameengine

import com.scorsi.gameengine.math.Vertex
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.lwjgl.BufferUtils

import java.nio.FloatBuffer
import java.nio.IntBuffer

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Util {

    static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size)
    }

    static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size)
    }

    static IntBuffer createFlippedBuffer(int ... values) {
        IntBuffer buffer = createIntBuffer(values.length)
        buffer.put(values)
        buffer.flip()

        return buffer
    }

    static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE)

        vertices.each { vertex ->
            buffer.put(vertex.position.x)
            buffer.put(vertex.position.y)
            buffer.put(vertex.position.z)
            buffer.put(vertex.textureCoordinate.x)
            buffer.put(vertex.textureCoordinate.y)
            buffer.put(vertex.normal.x)
            buffer.put(vertex.normal.y)
            buffer.put(vertex.normal.z)
        }

        buffer.flip()

        return buffer
    }

    static String[] removeEmptyStrings(String[] data) {
        ArrayList<String> result = new ArrayList<String>()

        for (int i = 0; i < data.length; i++)
            if (!data[i].equals(""))
                result.add(data[i])

        String[] res = new String[result.size()]
        result.toArray(res)

        return res
    }

    static int[] toIntArray(Integer[] data) {
        int[] result = new int[data.length]

        for (int i = 0; i < data.length; i++)
            result[i] = data[i].intValue()

        return result
    }

}
