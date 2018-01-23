package com.scorsi.gameengine.math

import groovy.transform.CompileStatic

import java.nio.FloatBuffer

/**
 * This class represents a (x,y,z,w)-Vector. GLSL equivalent to vec4.
 */
@CompileStatic
class Vector4f {

    float x
    float y
    float z
    float w

    /**
     * Creates a default 4-tuple vector with all values set to 0.
     */
    Vector4f() {
        this.x = 0f
        this.y = 0f
        this.z = 0f
        this.w = 0f
    }

    /**
     * Creates a 4-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     * @param w w value
     */
    Vector4f(float x, float y, float z, float w) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    float lengthSquared() {
        return x * x + y * y + z * z + w * w
    }

    /**
     * Calculates the length of the vector.
     *
     * @return Length of this vector
     */
    float length() {
        return Math.sqrt(lengthSquared())
    }

    /**
     * Normalizes the vector.
     *
     * @return Normalized vector
     */
    Vector4f normalize() {
        return divide(length())
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    Vector4f plus(Vector4f other) {
        def x = (this.x + other.x) as float
        def y = (this.y + other.y) as float
        def z = (this.z + other.z) as float
        def w = (this.w + other.w) as float
        return new Vector4f(x, y, z, w)
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    Vector4f negate() {
        return scale(-1f)
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    Vector4f minus(Vector4f other) {
        return this + other.negate()
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    Vector4f scale(float scalar) {
        def x = (this.x * scalar) as float
        def y = (this.y * scalar) as float
        def z = (this.z * scalar) as float
        def w = (this.w * scalar) as float
        return new Vector4f(x, y, z, w)
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    Vector4f divide(float scalar) {
        return scale(1f / scalar as float)
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    float dot(Vector4f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z + this.w * other.w
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    Vector4f lerp(Vector4f other, float alpha) {
        return this.scale(1f - alpha as float) + other.scale(alpha)
    }

    /**
     * Stores the vector in a given Buffer.
     *
     * @param buffer The buffer to store the vector data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z).put(w)
        buffer.flip()
    }

}
