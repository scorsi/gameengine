package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.ResourceLoader
import groovy.transform.CompileStatic

import static org.lwjgl.opengl.GL11.GL_TRUE
import static org.lwjgl.opengl.GL20.*
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER
import static org.lwjgl.system.MemoryUtil.NULL

/**
 * This class represents a shader.
 */
@CompileStatic
class Shader {

    /**
     * Stores the handle of the shader.
     */
    private final int id

    /**
     * Creates a shader with specified type. The type in the tutorial should be
     * either <code>GL_VERTEX_SHADER</code> or <code>GL_FRAGMENT_SHADER</code>.
     *
     * @param type Type of the shader
     */
    Shader(int type) {
        id = glCreateShader(type)

        if (id == NULL) {
            System.err.println("Shader creation failed: Could not find valid memory location when adding shader")
            System.exit(1)
        }
    }

    /**
     * Sets the source code of this shader.
     *
     * @param source GLSL Source Code for the shader
     */
    void source(CharSequence source) {
        glShaderSource(id, source)
    }

    /**
     * Compiles the shader and checks it's status afertwards.
     */
    void compile() {
        glCompileShader(id)

        checkStatus()
    }

    /**
     * Checks if the shader was compiled successfully.
     */
    private void checkStatus() {
        int status = glGetShaderi(id, GL_COMPILE_STATUS)
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetShaderInfoLog(id))
        }
    }

    /**
     * Deletes the shader.
     */
    void delete() {
        glDeleteShader(id)
    }

    /**
     * Getter for the shader ID.
     *
     * @return Handle of this shader
     */
    int getID() {
        return id
    }

    /**
     * Creates a shader with specified type and source and compiles it.
     *
     * @param type Type of the shader
     * @param source Source of the shader
     * @return Compiled Shader from the specified source
     */
    static Shader createShader(int type, CharSequence source) {
        Shader shader = new Shader(type)
        shader.source(source)
        shader.compile()

        return shader
    }

    /**
     * Creates a fragment shader with specified source and compiles it.
     *
     * @param source Source of the shader
     * @return Compiled Fragment Shader from the specified source
     */
    static Shader createFragmentShader(CharSequence source) {
        return createShader(GL_FRAGMENT_SHADER, source)
    }

    /**
     * Creates a vertex shader with specified source and compiles it.
     *
     * @param source Source of the shader
     * @return Compiled Vertex Shader from the specified source
     */
    static Shader createVertexShader(CharSequence source) {
        return createShader(GL_VERTEX_SHADER, source)
    }

    /**
     * Creates a geometry shader with specified source and compiles it.
     *
     * @param source Source of the shader
     * @return Compiled Geometry Shader from the specified source
     */
    static Shader createGeometryShader(CharSequence source) {
        return createShader(GL_GEOMETRY_SHADER, source)
    }

    /**
     * Loads a shader from a file with the specified type.
     *
     * @param type Type of the shader
     * @param path File path of the shader
     * @return Compiled Shader from specified file
     */
    static Shader loadShader(int type, String path) {
        return createShader(type, ResourceLoader.loadShader(path))
    }

    /**
     * Loads a vertex shader from a file.
     *
     * @param path File path of the shader
     * @return Compiled Shader from specified file
     */
    static Shader loadVertexShader(String path) {
        return createVertexShader(ResourceLoader.loadShader(path))
    }

    /**
     * Loads a fragment shader from a file.
     *
     * @param path File path of the shader
     * @return Compiled Shader from specified file
     */
    static Shader loadFragmentShader(String path) {
        return createFragmentShader(ResourceLoader.loadShader(path))
    }

    /**
     * Loads a geometry shader from a file.
     *
     * @param path File path of the shader
     * @return Compiled Shader from specified file
     */
    static Shader loadGeometryShader(String path) {
        return createGeometryShader(ResourceLoader.loadShader(path))
    }

}
