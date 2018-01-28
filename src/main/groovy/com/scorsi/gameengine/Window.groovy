package com.scorsi.gameengine

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.lwjgl.glfw.GLFWKeyCallback
import org.lwjgl.glfw.GLFWVidMode
import org.lwjgl.opengl.GL
import org.lwjgl.opengl.GLCapabilities

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
    final long id

    /**
     * Key callback for the window.
     */
    private final GLFWKeyCallback keyCallback

    /**
     * Shows if vsync is enabled.
     */
    private boolean vsync

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
        this.vsync = vsync

        /* Creating a temporary window for getting the available OpenGL version */
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        long temp = glfwCreateWindow(1, 1, "", NULL, NULL)
        glfwMakeContextCurrent(temp)
        GL.createCapabilities()
        GLCapabilities caps = GL.getCapabilities()
        glfwDestroyWindow(temp)

        /* Reset and set window hints */
        glfwDefaultWindowHints()
        glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE)

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

        /* Set key callback */
        keyCallback = new GLFWKeyCallback() {
            @Override
            void invoke(long window, int key, int scancode, int action, int mods) {
                if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                    glfwSetWindowShouldClose(window, true)
                }
            }
        }
        glfwSetKeyCallback(id, keyCallback)
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
     * Sets the window title
     *
     * @param title New window title
     */
    void setTitle(CharSequence title) {
        glfwSetWindowTitle(id, title)
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
        keyCallback.free()
    }

    /**
     * Setter for v-sync.
     *
     * @param vsync Set to true to enable v-sync
     */
    void setVSync(boolean vsync) {
        this.vsync = vsync
        if (vsync) {
            glfwSwapInterval(1)
        } else {
            glfwSwapInterval(0)
        }
    }

    /**
     * Check if v-sync is enabled.
     *
     * @return true if v-sync is enabled
     */
    boolean isVSyncEnabled() {
        return this.vsync
    }

}
