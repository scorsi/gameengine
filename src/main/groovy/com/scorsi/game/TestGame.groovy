package com.scorsi.game

import com.scorsi.engine.components.MeshRenderer
import com.scorsi.engine.components.MovableCamera
import com.scorsi.engine.core.Game
import com.scorsi.engine.core.GameObject
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Mesh
import com.scorsi.engine.rendering.ObjLoader
import com.scorsi.engine.rendering.Texture
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector3f

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class TestGame extends Game {

    void initialize() {

        def mesh = ObjLoader.loadMesh("cube.obj")
        def material = new Material()
        material.diffuseTexture = new Texture("grassblock.png")

        def cube1 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube1.transform.scale = new Vector3f(0.25f)
        cube1.transform.position = new Vector3f(0, 0, -2)
        def cube2 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube2.transform.scale = new Vector3f(0.25f)
        cube2.transform.position = new Vector3f(0.5f, 0.5f, -2)
        def cube3 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube3.transform.scale = new Vector3f(0.25f)
        cube3.transform.position = new Vector3f(0, 0, -2.5f)
        def cube4 = new GameObject().addComponent(new MeshRenderer(mesh, material))
        cube4.transform.scale = new Vector3f(0.25f)
        cube4.transform.position = new Vector3f(0.5f, 0, -2.5f)

        addGameObjects(cube1, cube2, cube3, cube4)

        def mainCamera = new MovableCamera()
        def cameraObject = new GameObject().addComponent(mainCamera)
        engine.renderingEngine.mainCamera = mainCamera
        addGameObject(cameraObject)
    }

}
