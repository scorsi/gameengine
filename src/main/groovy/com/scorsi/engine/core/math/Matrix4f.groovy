package com.scorsi.engine.core.math

import deleted.Vector4f
import groovy.transform.CompileStatic
import groovy.transform.ToString

import java.nio.FloatBuffer

/**
 * This class represents a 4x4-Matrix. GLSL equivalent to mat4.
 */
@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Matrix4f {

    private float m00, m01, m02, m03
    private float m10, m11, m12, m13
    private float m20, m21, m22, m23
    private float m30, m31, m32, m33

    /**
     * Creates a 4x4 identity matrix.
     */
    Matrix4f() {
        setIdentity()
    }

    /**
     * Creates a 4x4 matrix with specified columns.
     *
     * @param col1 Vector with values of the first column
     * @param col2 Vector with values of the second column
     * @param col3 Vector with values of the third column
     * @param col4 Vector with values of the fourth column
     */
    Matrix4f(Vector4f col1, Vector4f col2, Vector4f col3, Vector4f col4) {
        m00 = col1.x
        m10 = col1.y
        m20 = col1.z
        m30 = col1.w

        m01 = col2.x
        m11 = col2.y
        m21 = col2.z
        m31 = col2.w

        m02 = col3.x
        m12 = col3.y
        m22 = col3.z
        m32 = col3.w

        m03 = col4.x
        m13 = col4.y
        m23 = col4.z
        m33 = col4.w
    }

    /**
     * Sets this matrix to the identity matrix.
     */
    final Matrix4f setIdentity() {
        m00 = 1f
        m11 = 1f
        m22 = 1f
        m33 = 1f

        m01 = 0f
        m02 = 0f
        m03 = 0f
        m10 = 0f
        m12 = 0f
        m13 = 0f
        m20 = 0f
        m21 = 0f
        m23 = 0f
        m30 = 0f
        m31 = 0f
        m32 = 0f
        return this
    }

    // TODO: remove this method for a static one
    Matrix4f initRotation(float x, float y, float z) {
        def rx = new Matrix4f()
        def ry = new Matrix4f()
        def rz = new Matrix4f()

        x = Math.toRadians(x) as float
        y = Math.toRadians(y) as float
        z = Math.toRadians(z) as float

        rz.m00 = (float) Math.cos(z); rz.m01 = (float) -Math.sin(z); rz.m02 = 0f; rz.m03 = 0f
        rz.m10 = (float) Math.sin(z); rz.m11 = (float) Math.cos(z); rz.m12 = 0f; rz.m13 = 0f
        rz.m20 = 0f; rz.m21 = 0f; rz.m22 = 1f; rz.m23 = 0f
        rz.m30 = 0f; rz.m31 = 0f; rz.m32 = 0f; rz.m33 = 1f

        rx.m00 = 1f; rx.m01 = 0f; rx.m02 = 0f; rx.m03 = 0f
        rx.m10 = 0f; rx.m11 = (float) Math.cos(x); rx.m12 = (float) -Math.sin(x); rx.m13 = 0f
        rx.m20 = 0f; rx.m21 = (float) Math.sin(x); rx.m22 = (float) Math.cos(x); rx.m23 = 0f
        rx.m30 = 0f; rx.m31 = 0f; rx.m32 = 0f; rx.m33 = 1f

        ry.m00 = (float) Math.cos(y); ry.m01 = 0f; ry.m02 = (float) -Math.sin(y); ry.m03 = 0f
        ry.m10 = 0f; ry.m11 = 1f; ry.m12 = 0f; ry.m13 = 0f
        ry.m20 = (float) Math.sin(y); ry.m21 = 0f; ry.m22 = (float) Math.cos(y); ry.m23 = 0f
        ry.m30 = 0f; ry.m31 = 0f; ry.m32 = 0f; ry.m33 = 1f

        Matrix4f res = rz * ry * rx

        m00 = res.m00; m01 = res.m01; m02 = res.m02; m03 = res.m03
        m10 = res.m10; m11 = res.m11; m12 = res.m12; m13 = res.m13
        m20 = res.m20; m21 = res.m21; m22 = res.m22; m23 = res.m23
        m30 = res.m30; m31 = res.m31; m32 = res.m32; m33 = res.m33

        return this
    }

    // TODO: remove this method for a static one
    Matrix4f initRotation(Vector3f forward, Vector3f up) {
        def f = forward.normalize()
        def r = up.normalize().cross(f)
        def u = f.cross(r)

        m00 = r.x; m01 = r.y; m02 = r.z; m03 = 0f
        m10 = u.x; m11 = u.y; m12 = u.z; m13 = 0f
        m20 = f.x; m21 = f.y; m22 = f.z; m23 = 0f
        m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f

        return this
    }

    /**
     * Adds this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Sum of this + other
     */
    Matrix4f plus(Matrix4f other) {
        Matrix4f result = new Matrix4f()

        result.m00 = (this.m00 + other.m00) as float
        result.m10 = (this.m10 + other.m10) as float
        result.m20 = (this.m20 + other.m20) as float
        result.m30 = (this.m30 + other.m30) as float

        result.m01 = (this.m01 + other.m01) as float
        result.m11 = (this.m11 + other.m11) as float
        result.m21 = (this.m21 + other.m21) as float
        result.m31 = (this.m31 + other.m31) as float

        result.m02 = (this.m02 + other.m02) as float
        result.m12 = (this.m12 + other.m12) as float
        result.m22 = (this.m22 + other.m22) as float
        result.m32 = (this.m32 + other.m32) as float

        result.m03 = (this.m03 + other.m03) as float
        result.m13 = (this.m13 + other.m13) as float
        result.m23 = (this.m23 + other.m23) as float
        result.m33 = (this.m33 + other.m33) as float

        return result
    }

    /**
     * Negates this matrix.
     *
     * @return Negated matrix
     */
    Matrix4f negate() {
        return multiply(-1f)
    }

    /**
     * Subtracts this matrix from another matrix.
     *
     * @param other The other matrix
     * @return Difference of this - other
     */
    Matrix4f minus(Matrix4f other) {
        return this + other.negate()
    }

    /**
     * Multiplies this matrix with a scalar.
     *
     * @param scalar The scalar
     * @return Scalar product of this * scalar
     */
    Matrix4f multiply(float scalar) {
        Matrix4f result = new Matrix4f()

        result.m00 = (this.m00 * scalar) as float
        result.m10 = (this.m10 * scalar) as float
        result.m20 = (this.m20 * scalar) as float
        result.m30 = (this.m30 * scalar) as float

        result.m01 = (this.m01 * scalar) as float
        result.m11 = (this.m11 * scalar) as float
        result.m21 = (this.m21 * scalar) as float
        result.m31 = (this.m31 * scalar) as float

        result.m02 = (this.m02 * scalar) as float
        result.m12 = (this.m12 * scalar) as float
        result.m22 = (this.m22 * scalar) as float
        result.m32 = (this.m32 * scalar) as float

        result.m03 = (this.m03 * scalar) as float
        result.m13 = (this.m13 * scalar) as float
        result.m23 = (this.m23 * scalar) as float
        result.m33 = (this.m33 * scalar) as float

        return result
    }

    /**
     * Multiplies this matrix to a vector.
     *
     * @param vector The vector
     * @return Vector product of this * other
     */
    Vector4f multiply(Vector4f vector) {
        def x = (this.m00 * vector.x + this.m01 * vector.y + this.m02 * vector.z + this.m03 * vector.w) as float
        def y = (this.m10 * vector.x + this.m11 * vector.y + this.m12 * vector.z + this.m13 * vector.w) as float
        def z = (this.m20 * vector.x + this.m21 * vector.y + this.m22 * vector.z + this.m23 * vector.w) as float
        def w = (this.m30 * vector.x + this.m31 * vector.y + this.m32 * vector.z + this.m33 * vector.w) as float
        return new Vector4f(x, y, z, w)
    }

    /**
     * Multiplies this matrix to another matrix.
     *
     * @param other The other matrix
     * @return Matrix product of this * other
     */
    Matrix4f multiply(Matrix4f other) {
        Matrix4f result = new Matrix4f()

        result.m00 = (this.m00 * other.m00 + this.m01 * other.m10 + this.m02 * other.m20 + this.m03 * other.m30) as float
        result.m10 = (this.m10 * other.m00 + this.m11 * other.m10 + this.m12 * other.m20 + this.m13 * other.m30) as float
        result.m20 = (this.m20 * other.m00 + this.m21 * other.m10 + this.m22 * other.m20 + this.m23 * other.m30) as float
        result.m30 = (this.m30 * other.m00 + this.m31 * other.m10 + this.m32 * other.m20 + this.m33 * other.m30) as float

        result.m01 = (this.m00 * other.m01 + this.m01 * other.m11 + this.m02 * other.m21 + this.m03 * other.m31) as float
        result.m11 = (this.m10 * other.m01 + this.m11 * other.m11 + this.m12 * other.m21 + this.m13 * other.m31) as float
        result.m21 = (this.m20 * other.m01 + this.m21 * other.m11 + this.m22 * other.m21 + this.m23 * other.m31) as float
        result.m31 = (this.m30 * other.m01 + this.m31 * other.m11 + this.m32 * other.m21 + this.m33 * other.m31) as float

        result.m02 = (this.m00 * other.m02 + this.m01 * other.m12 + this.m02 * other.m22 + this.m03 * other.m32) as float
        result.m12 = (this.m10 * other.m02 + this.m11 * other.m12 + this.m12 * other.m22 + this.m13 * other.m32) as float
        result.m22 = (this.m20 * other.m02 + this.m21 * other.m12 + this.m22 * other.m22 + this.m23 * other.m32) as float
        result.m32 = (this.m30 * other.m02 + this.m31 * other.m12 + this.m32 * other.m22 + this.m33 * other.m32) as float

        result.m03 = (this.m00 * other.m03 + this.m01 * other.m13 + this.m02 * other.m23 + this.m03 * other.m33) as float
        result.m13 = (this.m10 * other.m03 + this.m11 * other.m13 + this.m12 * other.m23 + this.m13 * other.m33) as float
        result.m23 = (this.m20 * other.m03 + this.m21 * other.m13 + this.m22 * other.m23 + this.m23 * other.m33) as float
        result.m33 = (this.m30 * other.m03 + this.m31 * other.m13 + this.m32 * other.m23 + this.m33 * other.m33) as float

        return result
    }

    /**
     * Transposes this matrix.
     *
     * @return Transposed matrix
     */
    Matrix4f transpose() {
        Matrix4f result = new Matrix4f()

        result.m00 = this.m00
        result.m10 = this.m01
        result.m20 = this.m02
        result.m30 = this.m03

        result.m01 = this.m10
        result.m11 = this.m11
        result.m21 = this.m12
        result.m31 = this.m13

        result.m02 = this.m20
        result.m12 = this.m21
        result.m22 = this.m22
        result.m32 = this.m23

        result.m03 = this.m30
        result.m13 = this.m31
        result.m23 = this.m32
        result.m33 = this.m33

        return result
    }

    /**
     * Stores the matrix in a given Buffer.
     *
     * @param buffer The buffer to store the matrix data
     */
    void toBuffer(FloatBuffer buffer) {
        buffer.put(m00).put(m10).put(m20).put(m30)
        buffer.put(m01).put(m11).put(m21).put(m31)
        buffer.put(m02).put(m12).put(m22).put(m32)
        buffer.put(m03).put(m13).put(m23).put(m33)
        buffer.flip()
    }

    // TODO: test this function
    /**
     * Creates a orthographic projection matrix. Similar to
     * <code>glOrtho(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane
     * @param far Coordinate for the far depth clipping pane
     * @return Orthographic matrix
     */
    static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f ortho = new Matrix4f()

        def width = right - left as float
        def height = top - bottom as float
        def depth = far - near as float

        ortho.m00 = 2f / width as float
        ortho.m11 = 2f / height as float
        ortho.m22 = -2f / depth as float
        ortho.m03 = -(right + left) / width as float
        ortho.m13 = -(top + bottom) / height as float
        ortho.m23 = -(far + near) / depth as float

        return ortho
    }

    // TODO: test this function
    /**
     * Creates a perspective projection matrix. Similar to
     * <code>glFrustum(left, right, bottom, top, near, far)</code>.
     *
     * @param left Coordinate for the left vertical clipping pane
     * @param right Coordinate for the right vertical clipping pane
     * @param bottom Coordinate for the bottom horizontal clipping pane
     * @param top Coordinate for the bottom horizontal clipping pane
     * @param near Coordinate for the near depth clipping pane, must be
     *               positive
     * @param far Coordinate for the far depth clipping pane, must be
     *               positive
     * @return Perspective matrix
     */
    static Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f frustum = new Matrix4f()

        def a = ((right + left) / (right - left)) as float
        def b = ((top + bottom) / (top - bottom)) as float
        def c = (-(far + near) / (far - near)) as float
        def d = (-(2f * far * near) / (far - near)) as float

        frustum.m00 = ((2f * near) / (right - left)) as float
        frustum.m11 = ((2f * near) / (top - bottom)) as float
        frustum.m02 = a
        frustum.m12 = b
        frustum.m22 = c
        frustum.m32 = -1f
        frustum.m23 = d
        frustum.m33 = 0f

        return frustum
    }

    /**
     * Creates a perspective projection matrix. Similar to
     * <code>gluPerspective(fovy, aspec, zNear, zFar)</code>.
     *
     * @param fov Field of view angle in degrees
     * @param aspect The aspect ratio is the ratio of width to height
     * @param zNear Distance from the viewer to the near clipping plane, must
     *               be positive
     * @param zFar Distance from the viewer to the far clipping plane, must be
     *               positive
     * @return Perspective matrix
     */
    static Matrix4f perspective(float fov, float aspect, float zNear, float zFar) {
        def perspective = new Matrix4f()

        def f = Math.tan(Math.toRadians(fov / 2d)) as float

        perspective.m00 = 1f / (f * aspect) as float
        perspective.m11 = 1f / f as float
        perspective.m22 = ((-zNear - zFar) / (zNear - zFar)) as float
        perspective.m23 = ((2f * zFar * zNear) / (zNear - zFar)) as float
        perspective.m32 = 1f;
        perspective.m33 = 0f

        return perspective
    }

    /**
     * Creates a translation matrix. Similar to
     * <code>glTranslate(x, y, z)</code>.
     *
     * @param x x coordinate of translation vector
     * @param y y coordinate of translation vector
     * @param z z coordinate of translation vector
     * @return Translation matrix
     */
    static Matrix4f translate(float x, float y, float z) {
        Matrix4f translation = new Matrix4f()

        translation.m03 = x
        translation.m13 = y
        translation.m23 = z

        return translation
    }

    /**
     * Creates a rotation matrix. Similar to
     * <code>glRotate(angle, x, y, z)</code>.
     *
     * @param angle Angle of rotation in degrees
     * @param x x coordinate of the rotation vector
     * @param y y coordinate of the rotation vector
     * @param z z coordinate of the rotation vector
     * @return Rotation matrix
     */
    static Matrix4f rotate(float angle, float x, float y, float z) {
        def rotation = new Matrix4f()

        def c = Math.cos(Math.toRadians(angle)) as float
        def s = Math.sin(Math.toRadians(angle)) as float
        Vector3f vec = new Vector3f(x, y, z)
        if (vec.length() != 1f) {
            vec = vec.normalize()
            x = vec.x
            y = vec.y
            z = vec.z
        }

        rotation.m00 = (x * x * (1f - c) + c) as float
        rotation.m10 = (y * x * (1f - c) + z * s) as float
        rotation.m20 = (x * z * (1f - c) - y * s) as float
        rotation.m01 = (x * y * (1f - c) - z * s) as float
        rotation.m11 = (y * y * (1f - c) + c) as float
        rotation.m21 = (y * z * (1f - c) + x * s) as float
        rotation.m02 = (x * z * (1f - c) + y * s) as float
        rotation.m12 = (y * z * (1f - c) - x * s) as float
        rotation.m22 = (z * z * (1f - c) + c) as float

        return rotation
    }

    /**
     * Creates a scaling matrix. Similar to <code>glScale(x, y, z)</code>.
     *
     * @param x Scale factor along the x coordinate
     * @param y Scale factor along the y coordinate
     * @param z Scale factor along the z coordinate
     * @return Scaling matrix
     */
    static Matrix4f scale(float x, float y, float z) {
        Matrix4f scaling = new Matrix4f()

        scaling.m00 = x
        scaling.m11 = y
        scaling.m22 = z

        return scaling
    }

}
