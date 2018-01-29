package com.scorsi.game

import com.scorsi.engine.core.Engine
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Main {

    static void main(String[] args) {
        def engine = new Engine(new TestGame())
        engine.createWindow(800, 600, "test game")
        engine.framerate = 120
        engine.start()
        engine.dispose()
    }

}
