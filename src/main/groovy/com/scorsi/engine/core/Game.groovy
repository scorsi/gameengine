package com.scorsi.engine.core

import groovy.transform.CompileStatic

@CompileStatic
interface Game {

    void initialize(Engine engine)

    void input(Input input)

    void update()

    void render()

    void shutdown()

}
