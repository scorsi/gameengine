package com.scorsi.engine.core

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.camera.Camera
import com.scorsi.engine.rendering.shaders.BasicShader
import com.scorsi.engine.rendering.shaders.ForwardAmbientShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.opengl.GL11.GL_BACK
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT
import static org.lwjgl.opengl.GL11.GL_CULL_FACE
import static org.lwjgl.opengl.GL11.GL_CW
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D
import static org.lwjgl.opengl.GL11.GL_VERSION
import static org.lwjgl.opengl.GL11.glBindTexture
import static org.lwjgl.opengl.GL11.glClear
import static org.lwjgl.opengl.GL11.glClearColor
import static org.lwjgl.opengl.GL11.glCullFace
import static org.lwjgl.opengl.GL11.glDisable
import static org.lwjgl.opengl.GL11.glEnable
import static org.lwjgl.opengl.GL11.glFrontFace
import static org.lwjgl.opengl.GL11.glGetString
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class RenderingEngine {

    Camera mainCamera
    Vector3f ambientLight = new Vector3f(0.2f, 0.2f, 0.2f)

    RenderingEngine() {
        initGraphics()
    }

    private void initGraphics() {
        glClearColor(0f, 0f, 0f, 0f)

        glFrontFace(GL_CW)
        glCullFace(GL_BACK)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)

        glEnable(GL_DEPTH_CLAMP)

        setTexture(true)
    }

    void render(GameObject object) {
        def forwardAmbient = ForwardAmbientShader.instance
        forwardAmbient.renderingEngine = this
        clear()
        object.render(forwardAmbient)
    }

    private void clear() {
        // TODO: Stencil buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }

    private void setTexture(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D)
        else
            glDisable(GL_TEXTURE_2D)
    }

    private void unbindTextures() {
        glBindTexture(GL_TEXTURE_2D, 0)
    }

    static String getOpenGLVersion() {
        return glGetString(GL_VERSION)
    }

}
