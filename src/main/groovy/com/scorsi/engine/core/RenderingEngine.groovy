package com.scorsi.engine.core

import com.scorsi.engine.components.BaseLight
import com.scorsi.engine.components.Camera
import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.shaders.ForwardAmbientShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class RenderingEngine {

    Camera mainCamera
    Vector3f ambientLight = new Vector3f(0.2f, 0.2f, 0.2f)

    ArrayList<BaseLight> lights = new ArrayList<>()
    BaseLight activeLight

    RenderingEngine() {
        initGraphics()
    }

    private static final void initGraphics() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f)

        glFrontFace(GL_CW)
        glCullFace(GL_BACK)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)

        glEnable(GL_DEPTH_CLAMP)

        glEnable(GL_TEXTURE_2D)
    }

    private void clear() {
        // TODO: Stencil buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }

    void render(GameObject object) {
        clear()
        lights.clear()

        object.addToRenderingEngine(this)

        object.render(this, ForwardAmbientShader.instance)

        glEnable(GL_BLEND)
        glBlendFunc(GL_ONE, GL_ONE)
        glDepthMask(false)
        glDepthFunc(GL_EQUAL)

        lights.each { light ->
            activeLight = light
            object.render(this, light.shader)
        }

        glDepthFunc(GL_LESS)
        glDepthMask(true)
        glDisable(GL_BLEND)
    }

    static String getOpenGLVersion() {
        return glGetString(GL_VERSION)
    }

}
