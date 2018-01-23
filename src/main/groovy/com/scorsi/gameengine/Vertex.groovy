package com.scorsi.gameengine

import com.scorsi.gameengine.math.Vector2f
import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic

@CompileStatic
class Vertex {
    static final int SIZE = 5

    Vector3f position
    Vector2f textureCoordinate

    Vertex(Vector3f position) {
        this(position, new Vector2f(0f, 0f))
    }

    Vertex(Vector3f position, Vector2f textureCoordinate) {
        this.position = position
        this.textureCoordinate = textureCoordinate
    }

}
