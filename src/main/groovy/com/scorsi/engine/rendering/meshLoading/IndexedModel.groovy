package com.scorsi.engine.rendering.meshLoading

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector2f
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class IndexedModel {

    ArrayList<Vector3f> positions = new ArrayList<>()
    ArrayList<Vector2f> texturePosition = new ArrayList<>()
    ArrayList<Vector3f> normals = new ArrayList<>()
    ArrayList<Integer> indices = new ArrayList<>()

    void calcNormals() {
        for (int i = 0; i < indices.size(); i += 3) {
            int i0 = indices[i]
            int i1 = indices[i + 1]
            int i2 = indices[i + 2]

            def v1 = positions[i1].sub(positions[i0])
            def v2 = positions[i2].sub(positions[i0])

            def normal = v1.cross(v2).normalize()
            normals[i0] = normals[i0].add(normal)
            normals[i1] = normals[i1].add(normal)
            normals[i2] = normals[i2].add(normal)
        }

        normals.eachWithIndex { normal, i ->
            normals[i] = normal.normalize()
        }
    }

}
