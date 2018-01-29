package com.scorsi.engine.core

import com.scorsi.engine.rendering.Window
import groovy.transform.CompileStatic
import groovy.transform.ToString

import static org.lwjgl.glfw.GLFW.glfwInit
import static org.lwjgl.glfw.GLFW.glfwTerminate

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Engine {

    private double framerate = 60
    private double frametime = 1f / 60f
    private Game game
    RenderingEngine renderingEngine

    Window window
    Input input

    boolean isRunning

    Engine(Game game) {
        glfwInit()
        this.game = game
    }

    void createWindow(int width, int height, String title) {
        if (window != null) return
        window = new Window(width, height, title, true)
        input = new Input(window.id)
        renderingEngine = new RenderingEngine()
    }

    void setFramerate(double framerate) {
        this.framerate = framerate
        this.frametime = 1f / framerate
    }

    void start() {
        if (isRunning) return
        game.engine = this
        game.initialize()
        run()
    }

    void stop() {
        if (!isRunning) return
        isRunning = false
    }

    void dispose() {
        game.shutdown()
        clean()
        glfwTerminate()
    }

    private void run() {
        isRunning = true
        int frames = 0
        int frameCounter = 0

        double lastTime = Time.actualTime
        double unprocessedTime = 0

        while (isRunning) {
            boolean doRender = false

            double startTime = Time.actualTime
            double passedTime = startTime - lastTime
            lastTime = startTime

            unprocessedTime += passedTime
            frameCounter += passedTime as int

            if (frameCounter >= 1) {
                System.out.println("Frames: " + frames)
                frameCounter = 0
                frames = 0
            }

            while (unprocessedTime > frametime) {
                doRender = true

                unprocessedTime -= frametime

                input.update()
                game.input(frametime as float, input)
                game.update(frametime as float)
            }

            if (doRender) {
                renderingEngine.render(game.root)
                window.update()
                frames++
            } else {
                try {
                    Thread.sleep(1)
                } catch (InterruptedException e) {
                    e.printStackTrace()
                }
            }

            if (window.isClosing()) stop()
        }
    }

    private void clean() {
        window.destroy()
    }

}
