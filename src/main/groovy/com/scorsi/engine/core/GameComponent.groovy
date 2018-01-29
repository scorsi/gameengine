package com.scorsi.engine.core

import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true, excludes = "parent")
// excludes fix recursive toString call
abstract class GameComponent {

    GameObject parent

    void input(float delta, Input input) {}

    void update(float delta) {}

    void render(ShaderProgram shader) {}

    void addToRenderingEngine(RenderingEngine renderingEngine) {}

}
