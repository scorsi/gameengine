package com.scorsi.gameengine.graphics

import com.scorsi.gameengine.math.*
import groovy.transform.CompileStatic
import org.lwjgl.system.MemoryStack

import java.nio.FloatBuffer

import static org.lwjgl.opengl.GL11.GL_FLOAT
import static org.lwjgl.opengl.GL11.GL_TRUE
import static org.lwjgl.opengl.GL20.*
import static org.lwjgl.opengl.GL30.glBindFragDataLocation
import static org.lwjgl.system.MemoryUtil.NULL

/**
 * This class represents a shader program.
 *
 * @author Heiko Brumme
 */
@CompileStatic
class ShaderProgram {

    /**
     * Stores the handle of the program.
     */
    private final int id

    private ArrayList<Shader> shaders

    private HashMap<String, Integer> uniforms

    /**
     * Creates a shader program.
     */
    ShaderProgram() {
        id = glCreateProgram()

        if (id == NULL) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor")
            System.exit(1)
        }

        shaders = new ArrayList<>()
        uniforms = new HashMap<>()
    }

    /**
     * Attach a shader to this program.
     *
     * @param shader Shader to get attached
     */
    void attachShader(Shader shader) {
        glAttachShader(id, shader.getID())
        shaders.add(shader)
    }

    /**
     * Binds the fragment out color variable.
     *
     * @param number Color number you want to bind
     * @param name Variable name
     */
    void bindFragmentDataLocation(int number, CharSequence name) {
        glBindFragDataLocation(id, number, name)
    }

    /**
     * Link this program and check it's status afterwards.
     */
    void link() {
        glLinkProgram(id)

        checkStatus()
    }

    /**
     * Gets the location of an attribute variable with specified name.
     *
     * @param name Attribute name
     * @return Location of the attribute
     */
    int getAttributeLocation(CharSequence name) {
        return glGetAttribLocation(id, name)
    }

    /**
     * Enables a vertex attribute.
     *
     * @param location Location of the vertex attribute
     */
    void enableVertexAttribute(int location) {
        glEnableVertexAttribArray(location)
    }

    /**
     * Disables a vertex attribute.
     *
     * @param location Location of the vertex attribute
     */
    void disableVertexAttribute(int location) {
        glDisableVertexAttribArray(location)
    }

    /**
     * Sets the vertex attribute pointer.
     *
     * @param location Location of the vertex attribute
     * @param size Number of values per vertex
     * @param stride Offset between consecutive generic vertex attributes in
     *                 bytes
     * @param offset Offset of the first component of the first generic vertex
     *                 attribute in bytes
     */
    void pointVertexAttribute(int location, int size, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset)
    }

    /**
     * Gets the location of an uniform variable with specified name.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    void addUniformLocation(String name) {
        int uniformLocation = glGetUniformLocation(id, name)
        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error: Could not find uniform: " + name)
            new Exception().printStackTrace()
            System.exit(1)
        }
        uniforms.put(name, uniformLocation)
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, int value) {
        if (uniforms.containsKey(location))
            glUniform1i(uniforms.get(location), value)
        else {
            addUniformLocation(location)
            glUniform1i(uniforms.get(location), value)
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, float value) {
        if (uniforms.containsKey(location))
            glUniform1f(uniforms.get(location), value)
        else {
            addUniformLocation(location)
            glUniform1f(uniforms.get(location), value)
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Vector2f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(2)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniform2fv(uniforms.get(location), buffer)
            else {
                addUniformLocation(location)
                glUniform2fv(uniforms.get(location), buffer)
            }
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Vector3f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(3)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniform3fv(uniforms.get(location), buffer)
            else {
                addUniformLocation(location)
                glUniform3fv(uniforms.get(location), buffer)
            }
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Vector4f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(4)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniform4fv(uniforms.get(location), buffer)
            else {
                addUniformLocation(location)
                glUniform4fv(uniforms.get(location), buffer)
            }
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Matrix2f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(2 * 2)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniformMatrix2fv(uniforms.get(location), false, buffer)
            else {
                addUniformLocation(location)
                glUniformMatrix2fv(uniforms.get(location), false, buffer)
            }
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Matrix3f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(3 * 3)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniformMatrix3fv(uniforms.get(location), false, buffer)
            else {
                addUniformLocation(location)
                glUniformMatrix3fv(uniforms.get(location), false, buffer)
            }
        }
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    void setUniform(String location, Matrix4f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(4 * 4)
            value.toBuffer(buffer)

            if (uniforms.containsKey(location))
                glUniformMatrix4fv(uniforms.get(location), false, buffer)
            else {
                addUniformLocation(location)
                glUniformMatrix4fv(uniforms.get(location), false, buffer)
            }
        }
    }

    /**
     * Use this shader program.
     */
    void use() {
        glUseProgram(id)
    }

    /**
     * Checks if the program was linked successfully.
     */
    void checkStatus() {
        int status = glGetProgrami(id, GL_LINK_STATUS)
        if (status != GL_TRUE) {
            throw new RuntimeException(glGetProgramInfoLog(id))
        }
    }

    /**
     * Deletes the shader program.
     */
    void delete() {
        for (Shader shader : shaders) {
            shader.delete()
        }
        shaders.clear()
        glDeleteProgram(id)
    }

}
