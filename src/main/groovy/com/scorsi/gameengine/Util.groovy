package com.scorsi.gameengine

import groovy.transform.CompileStatic

import java.nio.FloatBuffer
import java.nio.IntBuffer

import org.lwjgl.BufferUtils

@CompileStatic
class Util {

    static FloatBuffer createFloatBuffer(int size) {
        return BufferUtils.createFloatBuffer(size)
    }

    static IntBuffer createIntBuffer(int size) {
        return BufferUtils.createIntBuffer(size)
    }

    static IntBuffer createFlippedBuffer(int... values) {
        IntBuffer buffer = createIntBuffer(values.length)
        buffer.put(values)
        buffer.flip()

        return buffer
    }

    static FloatBuffer createFlippedBuffer(Vertex[] vertices) {
        FloatBuffer buffer = createFloatBuffer(vertices.length * Vertex.SIZE)

        for (int i = 0; i < vertices.length; i++) {
            buffer.put(vertices[i].position.x)
            buffer.put(vertices[i].position.y)
            buffer.put(vertices[i].position.z)
            buffer.put(vertices[i].textureCoordinate.x)
            buffer.put(vertices[i].textureCoordinate.y)
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
