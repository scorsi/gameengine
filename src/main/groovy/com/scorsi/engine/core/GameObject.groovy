package com.scorsi.engine.core

import com.scorsi.engine.rendering.Transform
import com.scorsi.engine.rendering.shaders.ShaderProgram
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true, excludes = "parent")
// excludes fix recursive toString call
class GameObject {

    GameObject parent

    ArrayList<GameObject> children = new ArrayList<>()
    ArrayList<GameComponent> components = new ArrayList<>()
    Transform transform = new Transform()

    void input(float delta, Input input) {
        components.each {
            it.input(delta, input)
        }
        children.each {
            it.input(delta, input)
        }
    }

    void update(float delta) {
        components.each {
            it.update(delta)
        }
        children.each {
            it.update(delta)
        }
    }

    void render(ShaderProgram shader) {
        components.each {
            it.render(shader)
        }
        children.each {
            it.render(shader)
        }
    }

    GameObject addToRenderingEngine(RenderingEngine renderingEngine) {
        components.each {
            it.addToRenderingEngine(renderingEngine)
        }
        children.each {
            it.addToRenderingEngine(renderingEngine)
        }
        return this
    }

    GameObject addComponent(GameComponent component) {
        components.add(component)
        component.parent = this
        return this
    }

    GameObject addComponents(GameComponent... components) {
        components.each { addComponent(it) }
        return this
    }

    GameObject addChild(GameObject child) {
        children.add(child)
        child.parent = this
        return this
    }

    GameObject addChildren(GameObject... children) {
        children.each { addChild(it) }
        return this
    }

}
