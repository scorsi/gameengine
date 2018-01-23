package com.scorsi.gameengine.math

import groovy.transform.CompileStatic

import java.nio.FloatBuffer

/**
 * This class represents a (x,y,z)-Vector. GLSL equivalent to vec3.
 */
@CompileStatic
class Vector3f {

    float x
    float y
    float z

    /**
     * Creates a default 3-tuple vector with all values set to 0.
     */
    Vector3f() {
        this.x = 0f
        this.y = 0f
        this.z = 0f
    }

    /**
     * Creates a 3-tuple vector with specified values.
     *
     * @param x x value
     * @param y y value
     * @param z z value
     */
    Vector3f(float x, float y, float z) {
        this.x = x
        this.y = y
        this.z = z
    }

    /**
     * Calculates the squared length of the vector.
     *
     * @return Squared length of this vector
     */
    float lengthSquared() {
        return x * x + y * y + z * z
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
    Vector3f normalize() {
        return divide(length())
    }

    /**
     * Adds this vector to another vector.
     *
     * @param other The other vector
     * @return Sum of this + other
     */
    Vector3f add(Vector3f other) {
        def x = (this.x + other.x) as float
        def y = (this.y + other.y) as float
        def z = (this.z + other.z) as float
        return new Vector3f(x, y, z)
    }

    /**
     * Negates this vector.
     *
     * @return Negated vector
     */
    Vector3f negate() {
        return scale(-1f)
    }

    /**
     * Subtracts this vector from another vector.
     *
     * @param other The other vector
     * @return Difference of this - other
     */
    Vector3f subtract(Vector3f other) {
        return this.add(other.negate())
    }

    /**
     * Multiplies a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar product of this * scalar
     */
    Vector3f scale(float scalar) {
        def x = (this.x * scalar) as float
        def y = (this.y * scalar) as float
        def z = (this.z * scalar) as float
        return new Vector3f(x, y, z)
    }

    /**
     * Divides a vector by a scalar.
     *
     * @param scalar Scalar to multiply
     * @return Scalar quotient of this / scalar
     */
    Vector3f divide(float scalar) {
        return scale(1f / scalar as float)
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Dot product of this * other
     */
    float dot(Vector3f other) {
        return this.x * other.x + this.y * other.y + this.z * other.z
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param other The other vector
     * @return Cross product of this x other
     */
    Vector3f cross(Vector3f other) {
        def x = (this.y * other.z - this.z * other.y) as float
        def y = (this.z * other.x - this.x * other.z) as float
        def z = (this.x * other.y - this.y * other.x) as float
        return new Vector3f(x, y, z)
    }

    /**
     * Calculates a linear interpolation between this vector with another
     * vector.
     *
     * @param other The other vector
     * @param alpha The alpha value, must be between 0.0 and 1.0
     * @return Linear interpolated vector
     */
    Vector3f lerp(Vector3f other, float alpha) {
        return this.scale(1f - alpha as float).add(other.scale(alpha))
    }

    Vector3f rotate(float angle, Vector3f axis) {
        def sinHalfRadian = Math.sin(Math.toRadians(angle / 2 as double)) as float
        def cosHalfRadian = Math.cos(Math.toRadians(angle / 2 as double)) as float

        Quaternion rotation = new Quaternion(axis.x * sinHalfRadian as float, axis.y * sinHalfRadian as float, axis.z * sinHalfRadian as float, cosHalfRadian)
        Quaternion conjugate = rotation.conjugate()
        Quaternion w = rotation * this * conjugate

        return new Vector3f(w.x, w.y, w.z)
    }

    /**
     * Stores the vector in a given Buffer.
     *
     * @param buffer The buffer to store the vector data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(x).put(y).put(z)
        buffer.flip()
    }

}