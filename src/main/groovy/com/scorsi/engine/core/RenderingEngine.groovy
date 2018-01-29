package com.scorsi.engine.core

import com.scorsi.engine.core.math.Vector3f
import com.scorsi.engine.rendering.camera.Camera
import com.scorsi.engine.rendering.lights.Attenuation
import com.scorsi.engine.rendering.lights.DirectionalLight
import com.scorsi.engine.rendering.lights.PointLight
import com.scorsi.engine.rendering.lights.SpotLight
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
    Vector3f ambientLight
    DirectionalLight directionalLight
    DirectionalLight directionalLight2
    PointLight[] pointLights
    SpotLight[] spotLights

    RenderingEngine() {
        initGraphics()

        ambientLight = new Vector3f(0.2f, 0.2f, 0.2f)
        directionalLight = new DirectionalLight(new Vector3f(0, 0, 1), 0.4f, new Vector3f(1, 1, 1))
        directionalLight2 = new DirectionalLight(new Vector3f(1, 0, 0), 0.4f, new Vector3f(-1, 1, -1))

        int lightFieldWidth = 5
        int lightFieldDepth = 5

        float lightFieldStartX = 0
        float lightFieldStartY = 0
        float lightFieldStepX = 7
        float lightFieldStepY = 7

        pointLights = new PointLight[lightFieldWidth * lightFieldDepth]

        for (int i = 0; i < lightFieldWidth; i++) {
            for (int j = 0; j < lightFieldDepth; j++) {
                pointLights[i * lightFieldWidth + j] = new PointLight(new Vector3f(0, 1, 0), 0.4f,
                        new Vector3f(lightFieldStartX + lightFieldStepX * i as float, 0f, lightFieldStartY + lightFieldStepY * j as float),
                        new Attenuation(0, 0, 1), 100)
            }
        }

        spotLights = [new SpotLight(new Vector3f(0,1,1), 0.4f,
                new Vector3f(lightFieldStartX,0,lightFieldStartY),
                new Attenuation(0,0,0.1f), 100,
                new Vector3f(1,0,0), 0.7f)]
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

        forwardDirectional.directionalLight = directionalLight
        object.render(forwardDirectional)

        forwardDirectional.directionalLight = directionalLight2
        object.render(forwardDirectional)

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
