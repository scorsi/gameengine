package com.scorsi.engine.rendering.shaders

import com.scorsi.engine.components.BaseLight
import com.scorsi.engine.components.DirectionalLight
import com.scorsi.engine.components.PointLight
import com.scorsi.engine.components.SpotLight
import com.scorsi.engine.core.RenderingEngine
import com.scorsi.engine.rendering.Material
import com.scorsi.engine.rendering.Transform
import groovy.transform.CompileStatic
import groovy.transform.ToString
import org.joml.Matrix4f
import org.joml.Vector2f
import org.joml.Vector3f
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
@ToString(includePackage = false, includeNames = true)
class ShaderProgram {

    /**
     * Stores the handle of the program.
     */
    private final int id

    private ArrayList<Shader> shaders = new ArrayList<>()

    private HashMap<String, Integer> uniforms = new HashMap<>()

    /**
     * Creates a shader program.
     */
    ShaderProgram() {
        id = glCreateProgram()

        if (id == NULL) {
            System.err.println("Shader creation failed: Could not find valid memory location in constructor")
            System.exit(1)
        }
    }

    /**
     * Attach a shader to this program.
     *
     * @param shader Shader to get attached
     */
    ShaderProgram attachShader(Shader shader) {
        glAttachShader(id, shader.getID())
        shaders.add(shader)
        return this
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

    void bindAttributeLocation(int location, CharSequence name) {
        glBindAttribLocation(id, location, name)
    }

    /**
     * Link this program and check it's status afterwards.
     */
    ShaderProgram compile() {
        glLinkProgram(id)

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(id, 1024))
            System.exit(1)
        }

        glValidateProgram(id)

        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            System.err.println(glGetProgramInfoLog(id, 1024))
            System.exit(1)
        }
        return this
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
    static void enableVertexAttribute(int location) {
        glEnableVertexAttribArray(location)
    }

    /**
     * Disables a vertex attribute.
     *
     * @param location Location of the vertex attribute
     */
    static void disableVertexAttribute(int location) {
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
    static void pointVertexAttribute(int location, int size, int stride, int offset) {
        glVertexAttribPointer(location, size, GL_FLOAT, false, stride, offset)
    }

    /**
     * Add an uniform location.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addUniform(String name) {
        int uniformLocation = glGetUniformLocation(id, name)
        if (uniformLocation == 0xFFFFFFFF) {
            System.err.println("Error: Could not find uniform: " + name)
            new Exception().printStackTrace()
            System.exit(1)
        }
        uniforms.put(name, uniformLocation)
        return this
    }

    /**
     * Add multiples uniforms locations.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addUniform(String... names) {
        names.each { addUniform(it) }
        return this
    }

    /**
     * Add an uniform location based on DirectionalLight.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addBaseLightUniform(String name) {
        return addUniform(name + ".color")
                .addUniform(name + ".intensity")
    }

    /**
     * Add an uniform location based on DirectionalLight.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addDirectionalLightUniform(String name) {
        return addBaseLightUniform(name + ".base")
                .addUniform(name + ".direction")
    }

    /**
     * Add an uniform location based on Attenuation.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addAttenuationUniform(String name) {
        return addUniform(name + ".constant")
                .addUniform(name + ".linear")
                .addUniform(name + ".exponent")
    }

    /**
     * Add an uniform location based on PointLight.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addPointLightUniform(String name) {
        return addBaseLightUniform(name + ".base")
                .addUniform(name + ".position")
                .addAttenuationUniform(name + ".atten")
                .addUniform(name + ".range")
    }

    /**
     * Add an uniform location based on SpotLight.
     *
     * @param name Uniform name
     * @return Location of the uniform
     */
    ShaderProgram addSpotLightUniform(String name) {
        return addPointLightUniform(name + ".pointLight")
                .addUniform(name + ".direction")
                .addUniform(name + ".cutoff")
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, int value) {
        glUniform1i(uniforms[location], value)
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, float value) {
        glUniform1f(uniforms[location], value)
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, Vector2f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(2)
            value.get(buffer)

            glUniform2fv(uniforms[location], buffer)
        }
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, Vector3f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(3)
            value.get(buffer)

            glUniform3fv(uniforms[location], buffer)
        }
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, Matrix4f value) {
        MemoryStack.stackPush().withCloseable { stack ->
            FloatBuffer buffer = stack.mallocFloat(4 * 4)
            value.get(buffer)

            glUniformMatrix4fv(uniforms[location], false, buffer)
        }
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, BaseLight baseLight) {
        setUniform(location + ".color", baseLight.color)
        setUniform(location + ".intensity", baseLight.intensity)
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, DirectionalLight directionalLight) {
        setUniform(location + ".base", directionalLight as BaseLight)
        setUniform(location + ".direction", directionalLight.direction)
        return this
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, PointLight pointLight) {
        return setUniform(location + ".base", pointLight as BaseLight)
                .setUniform(location + ".atten.constant", pointLight.constant)
                .setUniform(location + ".atten.linear", pointLight.linear)
                .setUniform(location + ".atten.exponent", pointLight.exponent)
                .setUniform(location + ".position", pointLight.object.transform.position)
                .setUniform(location + ".range", pointLight.range)
    }

    /**
     * Sets the uniform variable for specified location.
     *
     * @param location Uniform location
     * @param value Value to set
     */
    ShaderProgram setUniform(String location, SpotLight spotLight) {
        return setUniform(location + ".pointLight", spotLight as PointLight)
                .setUniform(location + ".direction", spotLight.direction)
                .setUniform(location + ".cutoff", spotLight.cutoff)
    }

    ShaderProgram updateUniforms(RenderingEngine renderingEngine, Transform transform, Material material) {
        return this
    }

    /**
     * Use this shader program.
     */
    ShaderProgram use() {
        glUseProgram(id)
        return this
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
