package com.scorsi.gameengine.math

import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.nio.FloatBuffer

/**
 * This class represents a 2x2-Matrix. GLSL equivalent to mat2.
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Matrix2f {

    private float m00, m01
    private float m10, m11

    /**
     * Create a 2x2 identity matrix.
     */
    Matrix2f() {
        setIdentity()
    }

    /**
     * Creates a 2x2 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     */
    Matrix2f(Vector2f col1, Vector2f col2) {
        m00 = col1.x
        m10 = col1.y

        m01 = col2.x
        m11 = col2.y
    }

    /**
     * Sets this matrix to the identity matrix.
     */
    final void setIdentity() {
        m00 = 1f
        m11 = 1f

        m01 = 0f
        m10 = 0f
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Sum of this + other
     */
    Matrix2f plus(Matrix2f other) {
        def result = new Matrix2f()

        result.m00 = (this.m00 + other.m00) as float
        result.m10 = (this.m10 + other.m10) as float

        result.m01 = (this.m01 + other.m01) as float
        result.m11 = (this.m11 + other.m11) as float

        return result
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    Matrix2f negate() {
        return multiply(-1f)
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     * @return Difference of this - other
     */
    Matrix2f minus(Matrix2f other) {
        return this + other.negate()
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     * @return Scalar product of this * scalar
     */
    Matrix2f multiply(float scalar) {
        def result = new Matrix2f()

        result.m00 = (this.m00 * scalar) as float
        result.m10 = (this.m10 * scalar) as float

        result.m01 = (this.m01 * scalar) as float
        result.m11 = (this.m11 * scalar) as float

        return result
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     * @return Vector product of this * other
     */
    Vector2f multiply(Vector2f vector) {
        def x = (this.m00 * vector.x + this.m01 * vector.y) as float
        def y = (this.m10 * vector.x + this.m11 * vector.y) as float
        return new Vector2f(x, y)
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Matrix product of this * other
     */
    Matrix2f multiply(Matrix2f other) {
        def result = new Matrix2f()

        result.m00 = (this.m00 * other.m00 + this.m01 * other.m10) as float
        result.m10 = (this.m10 * other.m00 + this.m11 * other.m10) as float

        result.m01 = (this.m00 * other.m01 + this.m01 * other.m11) as float
        result.m11 = (this.m10 * other.m01 + this.m11 * other.m11) as float

        return result
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    Matrix2f transpose() {
        def result = new Matrix2f()

        result.m00 = this.m00
        result.m10 = this.m01

        result.m01 = this.m10
        result.m11 = this.m11

        return result
    }

    /**
     * Stores the matrix in a given Buffer.
     *
     * @param buffer The buffer to store the matrix data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10)
        buffer.put(m01).put(m11)
        buffer.flip()
    }

}