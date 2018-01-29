package com.scorsi.engine.core

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.camera.Camera
import com.scorsi.engine.rendering.lights.Attenuation
import com.scorsi.engine.components.DirectionalLight
import com.scorsi.engine.components.PointLight
import com.scorsi.engine.components.SpotLight
import com.scorsi.engine.rendering.shaders.ForwardAmbientShader
import com.scorsi.engine.rendering.shaders.ForwardDirectionalShader
import com.scorsi.engine.rendering.shaders.ForwardPointShader
import com.scorsi.engine.rendering.shaders.ForwardSpotShader
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.opengl.GL11.*
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class RenderingEngine {

    Camera mainCamera
    Vector3f ambientLight = new Vector3f(0.2f, 0.2f, 0.2f)

    ArrayList<DirectionalLight> directionalLights = new ArrayList<>()
    ArrayList<SpotLight> spotLights = new ArrayList<>()
    ArrayList<PointLight> pointLights = new ArrayList<>()

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

        directionalLights.clear()
        spotLights.clear()
        pointLights.clear()
    }

    void render(GameObject object) {
        clear()

        object.addToRenderingEngine(this)

        def forwardAmbient = ForwardAmbientShader.instance
        def forwardDirectional = ForwardDirectionalShader.instance
        def forwardPoint = ForwardPointShader.instance
        def forwardSpot = ForwardSpotShader.instance
        forwardAmbient.renderingEngine = this
        forwardDirectional.renderingEngine = this
        forwardPoint.renderingEngine = this
        forwardSpot.renderingEngine = this

        object.render(forwardAmbient)

        glEnable(GL_BLEND)
        glBlendFunc(GL_ONE, GL_ONE)
        glDepthMask(false)
        glDepthFunc(GL_EQUAL)

        directionalLights.each { directionalLight ->
            forwardDirectional.directionalLight = directionalLight
            object.render(forwardDirectional)
        }

        pointLights.each { pointLight ->
            forwardPoint.pointLight = pointLight
            object.render(forwardPoint)
        }

        spotLights.each { spotLight ->
            forwardSpot.spotLight = spotLight
            object.render(forwardSpot)
        }

        glDepthFunc(GL_LESS)
        glDepthMask(true)
        glDisable(GL_BLEND)
    }

    static String getOpenGLVersion() {
        return glGetString(GL_VERSION)
    }

}
