package com.scorsi.engine.core

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

    void input(float delta, Input input) {
        root.input(delta, input)
    }

    void update(float delta) {
        root.update(delta)
    }

    void shutdown() {}

}
