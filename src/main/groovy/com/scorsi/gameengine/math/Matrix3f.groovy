package com.scorsi.gameengine.math

import groovy.transform.CompileStatic

import java.nio.FloatBuffer

/**
 * This class represents a 3x3-Matrix. GLSL equivalent to mat3.
 */
@CompileStatic
class Matrix3f {

    private float m00, m01, m02
    private float m10, m11, m12
    private float m20, m21, m22

    /**
     * Creates a 3x3 identity matrix.
     */
    Matrix3f() {
        setIdentity()
    }

    /**
     * Creates a 3x3 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     * @param col3 Vector with values of the third column
     */
    Matrix3f(Vector3f col1, Vector3f col2, Vector3f col3) {
        m00 = col1.x
        m10 = col1.y
        m20 = col1.z

        m01 = col2.x
        m11 = col2.y
        m21 = col2.z

        m02 = col3.x
        m12 = col3.y
        m22 = col3.z
    }

    /**
     * Sets this matrix to the identity matrix.
     */
    final void setIdentity() {
        m00 = 1f
        m11 = 1f
        m22 = 1f

        m01 = 0f
        m02 = 0f
        m10 = 0f
        m12 = 0f
        m20 = 0f
        m21 = 0f
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Sum of this + other
     */
    Matrix3f add(Matrix3f other) {
        Matrix3f result = new Matrix3f()

        result.m00 = (this.m00 + other.m00) as float
        result.m10 = (this.m10 + other.m10) as float
        result.m20 = (this.m20 + other.m20) as float

        result.m01 = (this.m01 + other.m01) as float
        result.m11 = (this.m11 + other.m11) as float
        result.m21 = (this.m21 + other.m21) as float

        result.m02 = (this.m02 + other.m02) as float
        result.m12 = (this.m12 + other.m12) as float
        result.m22 = (this.m22 + other.m22) as float

        return result
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    Matrix3f negate() {
        return multiply(-1f)
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     * @return Difference of this - other
     */
    Matrix3f subtract(Matrix3f other) {
        return this.add(other.negate())
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     * @return Scalar product of this * scalar
     */
    Matrix3f multiply(float scalar) {
        def result = new Matrix3f()

        result.m00 = (this.m00 * scalar) as float
        result.m10 = (this.m10 * scalar) as float
        result.m20 = (this.m20 * scalar) as float

        result.m01 = (this.m01 * scalar) as float
        result.m11 = (this.m11 * scalar) as float
        result.m21 = (this.m21 * scalar) as float

        result.m02 = (this.m02 * scalar) as float
        result.m12 = (this.m12 * scalar) as float
        result.m22 = (this.m22 * scalar) as float

        return result
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     * @return Vector product of this * other
     */
    Vector3f multiply(Vector3f vector) {
        def x = (this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z) as float
        def y = (this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z) as float
        def z = (this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z) as float
        return new Vector3f(x, y, z)
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Matrix product of this * other
     */
    Matrix3f multiply(Matrix3f other) {
        def result = new Matrix3f()

        result.m00 = (this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20) as float
        result.m10 = (this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20) as float
        result.m20 = (this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20) as float

        result.m01 = (this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21) as float
        result.m11 = (this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21) as float
        result.m21 = (this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21) as float

        result.m02 = (this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22) as float
        result.m12 = (this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22) as float
        result.m22 = (this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22) as float

        return result
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    Matrix3f transpose() {
        Matrix3f result = new Matrix3f()

        result.m00 = this.m00
        result.m10 = this.m01
        result.m20 = this.m02

        result.m01 = this.m10
        result.m11 = this.m11
        result.m21 = this.m12

        result.m02 = this.m20
        result.m12 = this.m21
        result.m22 = this.m22

        return result
    }

    /**
     * Stores the matrix in a given Buffer.
     *
     * @param buffer The buffer to store the matrix data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20)
        buffer.put(m01).put(m11).put(m21)
        buffer.put(m02).put(m12).put(m22)
        buffer.flip()
    }

}