package com.scorsi.gameengine.utils;

public abstract class Vector3<Type> {

    protected Type x;
    protected Type y;
    protected Type z;

    public Vector3() {}

    public Vector3(Type x, Type y, Type z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3<Type> r) {
        this.x = r.getX();
        this.y = r.getY();
        this.z = r.getZ();
    }

    public abstract Type length();

    public abstract Type dot(Vector3<Type> r);

    public abstract Vector3<Type> cross(Vector3<Type> r);

    public abstract Vector3<Type> normalize();

    public abstract Vector3<Type> rotate(Type angle);

    public abstract Vector3<Type> add(Vector3<Type> r);

    public abstract Vector3<Type> add(Type r);

    public abstract Vector3<Type> sub(Vector3<Type> r);

    public abstract Vector3<Type> sub(Type r);

    public abstract Vector3<Type> mul(Vector3<Type> r);

    public abstract Vector3<Type> mul(Type r);

    public abstract Vector3<Type> div(Vector3<Type> r);

    public abstract Vector3<Type> div(Type r);

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
}
