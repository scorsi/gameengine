package com.scorsi.gameengine;

import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

public class MainComponent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "3D Engine";
    public static final double FRAME_CAP = 5000.0;

    public Window window;
    public Game game;
    public Input input;

    public boolean isRunning;

    public MainComponent() {
        window = new Window(WIDTH, HEIGHT, TITLE, true);
        game = new Game();
        input = new Input(window.id);
    }

    public void start() {
        if (isRunning) return;
        run();
    }

    public void stop() {
        if (!isRunning) return;
        isRunning = false;
    }

    private void run() {
        isRunning = true;

        final double frameTime = 1.0 / FRAME_CAP;
        int frames = 0;
        int frameCounter = 0;

        long lastTime = Time.getTime();
        double unprocessedTime = 0;

        while (isRunning) {
            boolean render = false;

            long startTime = Time.getTime();
            long passedTime = startTime - lastTime;
            lastTime = startTime;

            unprocessedTime += passedTime / (double) Time.SECOND;
            frameCounter += passedTime;

            while (unprocessedTime > frameTime) {
                render = true;
                unprocessedTime -= frameTime;

                Time.setDelta(frameTime);

                input.update();
                game.input(input);
                game.update();

                if (frameCounter >= Time.SECOND) {
                    System.out.println("Frames: " + frames);
                    frameCounter = 0;
                    frames = 0;
                }
            }

            if (render) {
                game.render();
                render();
                ++frames;
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (window.isClosing()) stop();
        }

        clean();
    }

    private void clean() {
        window.destroy();
    }

    private void render() {
        window.update();
    }

    public static void main(String[] args) {
        glfwInit();
        new MainComponent().start();
        glfwTerminate();
    }

}
