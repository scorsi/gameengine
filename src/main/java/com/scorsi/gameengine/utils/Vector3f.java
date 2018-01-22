package com.scorsi.gameengine.utils;

public class Vector3f extends Vector3<Float> {

    public Vector3f() {
    }

    public Vector3f(Float x, Float y, Float z) {
        super(x, y, z);
    }

    public Vector3f(Vector3<Float> r) {
        super(r);
    }

    @Override
    public Float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    @Override
    public Float dot(Vector3<Float> r) {
        return x * r.getX() + y * r.getY() + z * r.getZ();
    }

    @Override
    public Vector3f cross(Vector3<Float> r) {
        float x_ = y * r.getZ() - z * r.getY();
        float y_ = z * r.getX() - x * r.getZ();
        float z_ = x * r.getY() - y * r.getX();

        return new Vector3f(x_, y_, z_);
    }

    @Override
    public Vector3f normalize() {
        float length = this.length();
        x /= length;
        y /= length;
        z /= length;
        return this;
    }

    @Override
    public Vector3f rotate(Float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return null;
    }

    @Override
    public Vector3f add(Vector3<Float> r) {
        return new Vector3f(x + r.getX(), y + r.getY(), z + r.getZ());
    }

    @Override
    public Vector3f add(Float r) {
        return new Vector3f(x + r, y + r, z + r);
    }

    @Override
    public Vector3f sub(Vector3<Float> r) {
        return new Vector3f(x - r.getX(), y - r.getY(), z - r.getZ());
    }

    @Override
    public Vector3f sub(Float r) {
        return new Vector3f(x - r, y - r, z - r);
    }

    @Override
    public Vector3f mul(Vector3<Float> r) {
        return new Vector3f(x * r.getX(), y * r.getY(), z * r.getZ());
    }

    @Override
    public Vector3f mul(Float r) {
        return new Vector3f(x * r, y * r, z * r);
    }

    @Override
    public Vector3f div(Vector3<Float> r) {
        return new Vector3f(x / r.getX(), y / r.getY(), z / r.getZ());
    }

    @Override
    public Vector3f div(Float r) {
        return new Vector3f(x / r, y / r, z / r);
    }

}
