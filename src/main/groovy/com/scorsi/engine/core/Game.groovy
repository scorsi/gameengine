package com.scorsi.engine.core

import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic

@CompileStatic
abstract class Game {

    Engine engine
    GameObject root

    Game() {
        this.root = new GameObject()
    }

    void initialize() {

    }

    void input(Input input) {
        root.input(input)
    }

    void update() {
        root.update()
    }

    void shutdown() {}

}
