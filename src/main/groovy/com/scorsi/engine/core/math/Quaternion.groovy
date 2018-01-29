package com.scorsi.engine.core.math

import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Quaternion {

    float x
    float y
    float z
    float w

    Quaternion() {
        this.x = 0
        this.y = 0
        this.z = 0
        this.w = 1
    }

    Quaternion(Vector3f axis, float angle) {
        def rad = Math.toRadians(angle) as float
        def sinHalfAngle = Math.sin(rad / 2d) as float
        def cosHalfAngle = Math.cos(rad / 2d) as float

        x = axis.x * sinHalfAngle as float
        y = axis.y * sinHalfAngle as float
        z = axis.z * sinHalfAngle as float
        w = cosHalfAngle
    }

    Quaternion(float x, float y, float z, float w) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    float lengthSquared() {
        return x * x + y * y + z * z + w * w
    }

    float length() {
        return Math.sqrt(lengthSquared()) as float
    }

    Quaternion normalize() {
        return div(length())
    }

    Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w)
    }

    Quaternion multiply(Quaternion r) {
        def w_ = (w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ()) as float
        def x_ = (x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY()) as float
        def y_ = (y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ()) as float
        def z_ = (z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX()) as float

        return new Quaternion(x_, y_, z_, w_)
    }

    Quaternion multiply(Vector3f r) {
        def w_ = (-x * r.getX() - y * r.getY() - z * r.getZ()) as float
        def x_ = (w * r.getX() + y * r.getZ() - z * r.getY()) as float
        def y_ = (w * r.getY() + z * r.getX() - x * r.getZ()) as float
        def z_ = (w * r.getZ() + x * r.getY() - y * r.getX()) as float

        return new Quaternion(x_, y_, z_, w_)
    }

    Quaternion div(float r) {
        return new Quaternion(x / r as float, y / r as float, z / r as float, w / r as float)
    }

    Matrix4f toRotationMatrix() {
        def forward = new Vector3f(2.0f * (x * z - w * y) as float, 2.0f * (y * z + w * x) as float, 1.0f - 2.0f * (x * x + y * y) as float)
        def up = new Vector3f(2.0f * (x * y + w * z) as float, 1.0f - 2.0f * (x * x + z * z) as float, 2.0f * (y * z - w * x) as float)
        def right = new Vector3f(1.0f - 2.0f * (y * y + z * z) as float, 2.0f * (x * y - w * z) as float, 2.0f * (x * z + w * y) as float)

        return new Matrix4f().initRotation(forward, up, right)
    }

    Vector3f getForward() {
        return new Vector3f(0, 0, 1).rotate(this)
    }

    Vector3f getBack() {
        return new Vector3f(0, 0, -1).rotate(this)
    }

    Vector3f getUp() {
        return new Vector3f(0, 1, 0).rotate(this)
    }

    Vector3f getDown() {
        return new Vector3f(0, -1, 0).rotate(this)
    }

    Vector3f getRight() {
        return new Vector3f(1, 0, 0).rotate(this)
    }

    Vector3f getLeft() {
        return new Vector3f(-1, 0, 0).rotate(this)
    }

    Quaternion set(float x, float y, float z, float w) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
        return this
    }

    Quaternion set(Quaternion r) {
        this.x = r.x
        this.y = r.y
        this.z = r.z
        this.w = r.w
        return this
    }

    boolean equals(Quaternion r) {
        return x == r.x && y == r.y && z == r.z && w == r.w
    }

}
