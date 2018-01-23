package com.scorsi.gameengine.math

import com.scorsi.gameengine.math.Vector2f
import com.scorsi.gameengine.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Vertex {
    static final int SIZE = 8

    Vector3f position
    Vector2f textureCoordinate
    Vector3f normal

    Vertex(Vector3f position, Vector2f textureCoordinate = new Vector2f(), Vector3f normal = new Vector3f()) {
        this.position = position
        this.textureCoordinate = textureCoordinate
        this.normal = normal
    }

}
