package com.scorsi.gameengine

import groovy.transform.CompileStatic

import static org.lwjgl.glfw.GLFW.glfwInit
import static org.lwjgl.glfw.GLFW.glfwTerminate

@CompileStatic
class MainComponent {

    public static final int WIDTH = 800
    public static final int HEIGHT = 600
    public static final String TITLE = "3D Engine"
    public static final double FRAME_CAP = 5000.0

    public Window window
    public Game game
    public Input input

    public boolean isRunning

    MainComponent() {
        window = new Window(WIDTH, HEIGHT, TITLE, true)
        RenderUtil.initGraphics()
        game = new Game()
        input = new Input(window.id)

        System.out.println(RenderUtil.getOpenGLVersion())
    }

    void start() {
        if (isRunning) return
        run()
    }

    void stop() {
        if (!isRunning) return
        isRunning = false
    }

    private void run() {
        isRunning = true

        final double frameTime = 1.0 / FRAME_CAP
        int frames = 0
        int frameCounter = 0

        long lastTime = Time.getTime()
        double unprocessedTime = 0

        while (isRunning) {
            boolean doRender = false

            long startTime = Time.getTime()
            long passedTime = startTime - lastTime
            lastTime = startTime

            unprocessedTime += passedTime / (double) Time.SECOND
            frameCounter += passedTime as int

            while (unprocessedTime > frameTime) {
                doRender = true
                unprocessedTime -= frameTime

                Time.delta = frameTime

                input.update()
                game.input(input)
                game.update()

                if (frameCounter >= Time.SECOND) {
                    System.out.println("Frames: " + frames)
                    frameCounter = 0
                    frames = 0
                }
            }

            if (doRender) {
                render()
                ++frames
            } else {
                try {
                    Thread.sleep(1)
                } catch (InterruptedException e) {
                    e.printStackTrace()
                }
            }

            if (window.isClosing()) stop()
        }

        clean()
    }

    private void clean() {
        window.destroy()
    }

    private void render() {
        RenderUtil.clear()
        game.render()
        window.update()
    }

    static void main(String[] args) {
        glfwInit()
        new MainComponent().start()
        glfwTerminate()
    }

}
