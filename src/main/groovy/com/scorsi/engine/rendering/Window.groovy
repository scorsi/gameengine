package com.scorsi.engine.rendering

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector2f
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL

import static org.lwjgl.glfw.GLFW.*
import static org.lwjgl.system.MemoryUtil.NULL

/**
 * This class represents a window.
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Window {

    /**
     * Stores the window handle.
     */
    private final long id

    long getId() { id }

    /**
     * Shows if vsync is enabled.
     */
    private boolean vsync

    boolean getVsync() { vsync }

    /**
     * The width and height of the window
     */
    private float width

    float getWidth() { width }
    private float height

    float getHeight() { height }

    /**
     *
     */
    private boolean resized

    boolean getResized() { resized }

    /**
     * Creates a GLFW window and its OpenGL context with the specified width,
     * height and title.
     *
     * @param width Width of the drawing area
     * @param height Height of the drawing area
     * @param title Title of the window
     * @param vsync Set to true, if you want v-sync
     */
    Window(int width, int height, CharSequence title, boolean vsync) {
        this.width = width as float
        this.height = height as float
        this.vsync = vsync

        /* Show OpenGL errors */
//        GLFWErrorCallback.createPrint(System.err).set()

        /* Set options for our window */
        glfwDefaultWindowHints() // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE) // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE) // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE)

        /* Create window with specified OpenGL context */
        id = glfwCreateWindow(width, height, title, NULL, NULL)
        if (id == NULL) {
            glfwTerminate()
            throw new RuntimeException("Failed to create the window!")
        }

        /* Center window on screen */
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor())
        glfwSetWindowPos(id,
                (vidmode.width() - width) / 2 as int,
                (vidmode.height() - height) / 2 as int
        )

        /* Create OpenGL context */
        glfwMakeContextCurrent(id)
        GL.createCapabilities()

        /* Enable v-sync */
        if (vsync) {
            glfwSwapInterval(1)
        }

        /* Set resize callback */
        glfwSetFramebufferSizeCallback(id, { window, float newWidth, float newHeight ->
            this.width = newWidth
            this.height = newHeight
            this.resized = true
        })

        /* Show the window */
        glfwShowWindow(id)
    }

    /**
     * Returns if the window is closing.
     *
     * @return true if the window should close, else false
     */
    boolean isClosing() {
        return glfwWindowShouldClose(id)
    }

    /**
     * Updates the screen.
     */
    void update() {
        glfwSwapBuffers(id)
        glfwPollEvents()
    }

    /**
     * Destroys the window an releases its callbacks.
     */
    void destroy() {
        glfwDestroyWindow(id)
    }

    /**
     * Setter for v-sync.
     *
     * @param vsync Set to true to enable v-sync
     */
    void setVsync(boolean vsync) {
        this.vsync = vsync
        if (vsync) {
            glfwSwapInterval(1)
        } else {
            glfwSwapInterval(0)
        }
    }

    /**
     * Get the center of the window.
     *
     * @return
     */
    Vector2f getCenter() {
        return new Vector2f(width / 2f as float, height / 2 as float)
    }

}
