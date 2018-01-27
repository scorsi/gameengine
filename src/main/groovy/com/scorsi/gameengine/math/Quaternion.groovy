package com.scorsi.gameengine.math

import groovy.transform.ToString

@ToString(includePackage = false, includeNames = true)
class Quaternion {

    float x
    float y
    float z
    float w

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
        return (float) Math.sqrt(lengthSquared())
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

}
