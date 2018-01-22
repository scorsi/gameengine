package com.scorsi.gameengine.utils;

public class Quaternionf extends Quaternion<Float> {

    public Quaternionf(Float x, Float y, Float z, Float w) {
        super(x, y, z, w);
    }

    @Override
    public Float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    @Override
    public Quaternionf normalize() {
        float length = this.length();
        x /= length;
        y /= length;
        z /= length;
        w /= length;
        return this;
    }

    @Override
    public Quaternionf conjugate() {
        return new Quaternionf(-x, -y, -z, w);
    }

    @Override
    public Quaternion<Float> mul(Quaternion<Float> r) {
        float w_ = w * r.getW() - x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = x * r.getW() + w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = y * r.getW() + w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = z * r.getW() + w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternionf(x_, y_, z_, w_);
    }

    @Override
    public Quaternion<Float> mul(Vector3<Float> r) {
        float w_ = -x * r.getX() - y * r.getY() - z * r.getZ();
        float x_ = w * r.getX() + y * r.getZ() - z * r.getY();
        float y_ = w * r.getY() + z * r.getX() - x * r.getZ();
        float z_ = w * r.getZ() + x * r.getY() - y * r.getX();

        return new Quaternionf(x_, y_, z_, w_);
    }

}
