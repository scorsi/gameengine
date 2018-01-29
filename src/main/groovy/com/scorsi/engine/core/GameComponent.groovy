package com.scorsi.engine.core

import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true, excludes = "object")
// excludes fix recursive toString call
abstract class GameComponent {

    GameObject object

    GameComponent(GameObject object) {
        this.object = object
    }

    void input(Input input) {}

    void update() {}

    void render(ShaderProgram shader) {}

}
