package com.scorsi.game

import com.scorsi.engine.components.MeshRenderer
import com.scorsi.engine.components.MovableCamera
import com.scorsi.engine.core.Game
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.Texture
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class TestGame extends Game {

    void initialize() {
        // Create the Mesh
        float[] positions = [
                // V0
                -0.5f, 0.5f, 0.5f,
                // V1
                -0.5f, -0.5f, 0.5f,
                // V2
                0.5f, -0.5f, 0.5f,
                // V3
                0.5f, 0.5f, 0.5f,
                // V4
                -0.5f, 0.5f, -0.5f,
                // V5
                0.5f, 0.5f, -0.5f,
                // V6
                -0.5f, -0.5f, -0.5f,
                // V7
                0.5f, -0.5f, -0.5f,
                // For text coords in top face
                // V8: V4 repeated
                -0.5f, 0.5f, -0.5f,
                // V9: V5 repeated
                0.5f, 0.5f, -0.5f,
                // V10: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V11: V3 repeated
                0.5f, 0.5f, 0.5f,
                // For text coords in right face
                // V12: V3 repeated
                0.5f, 0.5f, 0.5f,
                // V13: V2 repeated
                0.5f, -0.5f, 0.5f,
                // For text coords in left face
                // V14: V0 repeated
                -0.5f, 0.5f, 0.5f,
                // V15: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // For text coords in bottom face
                // V16: V6 repeated
                -0.5f, -0.5f, -0.5f,
                // V17: V7 repeated
                0.5f, -0.5f, -0.5f,
                // V18: V1 repeated
                -0.5f, -0.5f, 0.5f,
                // V19: V2 repeated
                0.5f, -0.5f, 0.5f,]
        float[] textCoords = [
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                // For text coords in top face
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                // For text coords in right face
                0.0f, 0.0f,
                0.0f, 0.5f,
                // For text coords in left face
                0.5f, 0.0f,
                0.5f, 0.5f,
                // For text coords in bottom face
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,]
        int[] indices = [
                // Front face
                0, 1, 3, 3, 1, 2,
                // Top Face
                8, 10, 11, 9, 8, 11,
                // Right face
                12, 13, 7, 5, 12, 7,
                // Left face
                14, 15, 6, 4, 14, 6,
                // Bottom face
                16, 18, 19, 17, 16, 19,
                // Back face
                4, 6, 7, 5, 4, 7,]

        def mesh = new Mesh(positions, textCoords, indices, new Texture("grassblock.png"))
        def material = new Material()
//        material.textures["diffuse"] = new Texture("grassblock.png")
        material.floats["specularIntensity"] = 1f
        material.floats["specularPower"] = 8f

        def cube1 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube1.transform.scale = new Vector3f(0.5f)
        cube1.transform.position = new Vector3f(0, 0, -2)
        def cube2 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube2.transform.scale = new Vector3f(0.5f)
        cube2.transform.position = new Vector3f(0.5f, 0.5f, -2)
        def cube3 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube3.transform.scale = new Vector3f(0.5f)
        cube3.transform.position = new Vector3f(0, 0, -2.5f)
        def cube4 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube4.transform.scale = new Vector3f(0.5f)
        cube4.transform.position = new Vector3f(0.5f, 0, -2.5f)

        addGameObjects(cube1, cube2, cube3, cube4)

        def mainCamera = new MovableCamera()
        def cameraObject = new GameObject().addComponent(mainCamera)
        engine.renderingEngine.mainCamera = mainCamera
        addGameObject(cameraObject)
    }

}
