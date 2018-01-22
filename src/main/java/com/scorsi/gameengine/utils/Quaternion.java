package com.scorsi.gameengine.utils;

public abstract class Quaternion<Type> {

    protected Type x;
    protected Type y;
    protected Type z;
    protected Type w;

    public Quaternion(Type x, Type y, Type z, Type w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public abstract Type length();

    public abstract Quaternion<Type> normalize();

    public abstract Quaternion<Type> conjugate();

    public abstract Quaternion<Type> mul(Quaternion<Type> r);

    public abstract Quaternion<Type> mul(Vector3<Type> r);

    public Type getX() {
        return x;
    }

    public void setX(Type x) {
        this.x = x;
    }

    public Type getY() {
        return y;
    }

    public void setY(Type y) {
        this.y = y;
    }

    public Type getZ() {
        return z;
    }

    public void setZ(Type z) {
        this.z = z;
    }

    public Type getW() {
        return w;
    }

    public void setW(Type w) {
        this.w = w;
    }
}
