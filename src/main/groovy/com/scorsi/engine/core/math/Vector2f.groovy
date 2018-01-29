package com.scorsi.engine.core.math

import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.nio.FloatBuffer

/**
 * This class represents a (x,y)-Vector. GLSL equivalent to vec2.
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Vector2f {

    float x
    float y

    /**
     * Creates a default 2-tuple vector with all values set to 0.
     */
    Vector2f() {
        this.x = 0f
        this.y = 0f
    }

    /**
     * Creates a 2-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     */
    Vector2f(float x, float y) {
        this.x = x
        this.y = y
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    float lengthSquared() {
        return x * x + y * y
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
    Vector2f normalize() {
        return divide(length())
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    Vector2f plus(Vector2f other) {
        def x = this.x + other.x as float
        def y = this.y + other.y as float
        return new Vector2f(x, y)
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    Vector2f negate() {
        return scale(-1f)
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    Vector2f minus(Vector2f other) {
        return this + other.negate()
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    Vector2f scale(float scalar) {
        def x = this.x * scalar as float
        def y = this.y * scalar as float
        return new Vector2f(x, y)
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    Vector2f divide(float scalar) {
        return scale(1f / scalar as float)
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    float dot(Vector2f other) {
        return this.x * other.x + this.y * other.y
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    Vector2f lerp(Vector2f other, float alpha) {
        return this.scale(1f - alpha as float) + other.scale(alpha)
    }

    float max() {
        return Math.max(x, y)
    }

    Vector2f set(float x, float y) {
        this.x = x
        this.y = y
        return this
    }

    Vector2f set(Vector2f r) {
        this.x = r.x
        this.y = r.y
        return this
    }

    /**
     * Stores the vector in a given Buffer.
     *
     * @param buffer The buffer to store the vector data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y)
        buffer.flip()
    }

    boolean equals(Vector2f r) {
        return x == r.x && y == r.y
    }
}