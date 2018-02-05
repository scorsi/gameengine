package com.scorsi.engine.rendering.meshLoading

import com.scorsi.engine.core.Utils
import com.scorsi.engine.core.math.Vector2f
import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class ObjModel {

    @EqualsAndHashCode
    class ObjIndex {

        int vertexIndex = 0
        int texturePositionIndex = 0
        int normalIndex = 0

    }

    ArrayList<Vector3f> positions = new ArrayList<>()
    ArrayList<Vector2f> texturePositions = new ArrayList<>()
    ArrayList<Vector3f> normals = new ArrayList<>()
    ArrayList<ObjIndex> indices = new ArrayList<>()
    private Boolean hasTexturePositions = false
    private Boolean hasNormals = false

    ObjModel(String filename) {
        try {
            new BufferedReader(new FileReader("./res/models/" + filename)).withCloseable { meshReader ->
                String line

                while ((line = meshReader.readLine()) != null) {
                    def tokens = line.split(" ")
                    tokens = Utils.removeEmptyStrings(tokens)

                    if (tokens.length == 0 || tokens[0] == "#")
                        continue
                    else if (tokens[0] == "v") {
                        positions.add(new Vector3f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2]),
                                Float.valueOf(tokens[3])))
                    } else if (tokens[0] == "vt") {
                        texturePositions.add(new Vector2f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2])))
                    } else if (tokens[0] == "vn") {
                        normals.add(new Vector3f(Float.valueOf(tokens[1]),
                                Float.valueOf(tokens[2]),
                                Float.valueOf(tokens[3])))
                    } else if (tokens[0] == "f") {
                        for (i in 0..tokens.length - 3 - 1) {
                            indices.add(parseObjIndex(tokens[1]))
                            indices.add(parseObjIndex(tokens[2 + i]))
                            indices.add(parseObjIndex(tokens[3 + i]))
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace()
            System.exit(1)
        }
    }

    private ObjIndex parseObjIndex(String token) {
        def values = token.split("/")
        def objIndex = new ObjIndex()

        objIndex.vertexIndex = Integer.parseInt(values[0]) - 1

        if (values.length > 1) {
            hasTexturePositions = true
            objIndex.texturePositionIndex = Integer.parseInt(values[1]) - 1
            if (values.length > 2) {
                hasNormals = true
                objIndex.normalIndex = Integer.parseInt(values[2]) - 1
            }
        }

        return objIndex
    }

    IndexedModel toIndexedModel() {
        def resultModel = new IndexedModel()
        def normalModel = new IndexedModel()
        def resultIndexMap = new HashMap<ObjIndex, Integer>()
        def normalIndexMap = new HashMap<Integer, Integer>()
        def indexMap = new HashMap<Integer, Integer>()

        indices.eachWithIndex { index, i ->
            def position = positions[index.vertexIndex]
            def texturePosition = with {
                if (hasTexturePositions) texturePositions[index.texturePositionIndex]
                else new Vector2f()
            } as Vector2f
            def normal = with {
                if (hasNormals) normals[index.normalIndex]
                else new Vector3f()
            } as Vector3f

            def modelVertexIndex = resultIndexMap[index]
            if (modelVertexIndex == null) {
                modelVertexIndex = resultModel.positions.size()
                resultIndexMap.put(index, modelVertexIndex)

                resultModel.positions.add(position)
                resultModel.texturePosition.add(texturePosition)
                if (hasNormals)
                    resultModel.normals.add(normal)
            }
            resultModel.indices.add(modelVertexIndex)

            def normalModelIndex = normalIndexMap[index.vertexIndex]
            if (normalModelIndex == null) {
                normalModelIndex = normalModel.positions.size()
                normalIndexMap.put(index.vertexIndex, normalModelIndex)

                normalModel.positions.add(position)
                normalModel.texturePosition.add(texturePosition)
                normalModel.normals.add(normal)
            }
            normalModel.indices.add(normalModelIndex)

            indexMap.put(modelVertexIndex, normalModelIndex)
        }

        if (!hasNormals) {
            normalModel.calcNormals()
            for (i in 0..resultModel.positions.size() - 1) {
                resultModel.normals.add(normalModel.normals[indexMap[i]])
            }
        }

        return resultModel
    }

}
