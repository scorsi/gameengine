package com.scorsi.engine.core

import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Vector2f

import static org.lwjgl.glfw.GLFW.*

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Input {

    private static final int NUM_KEYCODES = GLFW_KEY_LAST

    private boolean[] currentKeys = new boolean[NUM_KEYCODES]
    private boolean[] pressedKeys = new boolean[NUM_KEYCODES]
    private boolean[] releasedKeys = new boolean[NUM_KEYCODES]

    private static final int NUM_MOUSEBUTTONS = 8

    private boolean[] currentMouses = new boolean[NUM_MOUSEBUTTONS]
    private boolean[] pressedMouses = new boolean[NUM_MOUSEBUTTONS]
    private boolean[] releasedMouses = new boolean[NUM_MOUSEBUTTONS]

    /**
     * Current mouse position
     */
    private Vector2f currentPos = new Vector2f()

    Vector2f getCurrentPos() { currentPos }

    /**
     * Last mouse position
     */
    private Vector2f previousPos = new Vector2f()

    Vector2f getPreviousPos() { previousPos }

    /**
     * Is the mouse is in the window
     */
    private boolean inWindow = false

    boolean getInWindow() { inWindow }

    Input(long windowId) {
        /** Set the cursor position callback */
        glfwSetCursorPosCallback(windowId, { windowHandle, xpos, ypos ->
            previousPos.x = currentPos.x
            previousPos.y = currentPos.y
            currentPos.x = xpos as float
            currentPos.y = ypos as float
        })
        /** Set the cursor enter callback */
        glfwSetCursorEnterCallback(windowId, { window, entered ->
            inWindow = entered
        })
        /** Set the mouse button callback */
        glfwSetMouseButtonCallback(windowId, { window, int button, int action, mode ->
            if (action == GLFW_PRESS) {
                pressedMouses[button] = true
                currentMouses[button] = false
                releasedMouses[button] = false
            } else if (action == GLFW_REPEAT) {
                pressedMouses[button] = false
                currentMouses[button] = true
                releasedMouses[button] = false
            } else if (action == GLFW_RELEASE) {
                pressedMouses[button] = false
                currentMouses[button] = false
                releasedMouses[button] = true
            }
        })
        /** Set the key callback */
        glfwSetKeyCallback(windowId, { long window, int key, scancode, action, mods ->
            if (action == GLFW_PRESS) {
                pressedKeys[key] = true
                currentKeys[key] = false
                releasedKeys[key] = false
            } else if (action == GLFW_REPEAT) {
                pressedKeys[key] = false
                currentKeys[key] = true
                releasedKeys[key] = false
            } else if (action == GLFW_RELEASE) {
                pressedKeys[key] = false
                currentKeys[key] = false
                releasedKeys[key] = true
            }
        })
    }

    boolean isKeyPressed(int key) {
        currentKeys[key] || pressedKeys[key]
    }

    boolean isKeyPressedOnce(int key) {
        pressedKeys[key]
    }

    boolean isKeyReleased(int key) {
        releasedKeys[key]
    }

    boolean isMousePressed(int button) {
        currentMouses[button] || pressedMouses[button]
    }

    boolean isMousePressedOnce(int button) {
        pressedMouses[button]
    }

    boolean isMouseReleased(int button) {
        releasedMouses[button]
    }

}
