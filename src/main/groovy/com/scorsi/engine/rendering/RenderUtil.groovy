package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL32.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class RenderUtil {
    static void clear() {
        // TODO: Stencil buffer
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT)
    }

    static void setTexture(boolean enabled) {
        if (enabled)
            glEnable(GL_TEXTURE_2D)
        else
            glDisable(GL_TEXTURE_2D)
    }

    static void initGraphics() {
        glClearColor(0f, 0f, 0f, 0f)

        glFrontFace(GL_CW)
        glCullFace(GL_BACK)
        glEnable(GL_CULL_FACE)
        glEnable(GL_DEPTH_TEST)

        glEnable(GL_DEPTH_CLAMP)

        setTexture(true)
    }

    static String getOpenGLVersion() {
        return glGetString(GL_VERSION)
    }
}
