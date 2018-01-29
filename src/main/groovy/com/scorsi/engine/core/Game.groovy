package com.scorsi.engine.core

import groovy.transform.CompileStatic

@CompileStatic
abstract class Game {

    Engine engine
    private GameObject root

    Game() {
        this.root = new GameObject()
    }

    void initialize() {

    }

    void input(float delta, Input input) {
        root.input(delta, input)
    }

    void render(RenderingEngine renderingEngine) {
        renderingEngine.render(root)
    }

    void update(float delta) {
        root.update(delta)
    }

    void shutdown() {}

    Game addGameObject(GameObject object) {
        root.addChild(object)
        return this
    }

    Game addGameObjects(GameObject... objects) {
        objects.each { addGameObject(it) }
        return this
    }

}
