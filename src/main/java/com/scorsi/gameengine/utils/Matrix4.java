package com.scorsi.gameengine.utils;

public abstract class Matrix4<Type> {

    protected Type[][] m;

    public abstract Matrix4<Type> initIdentity();

    public abstract Matrix4<Type> mul(Matrix4<Type> r);

    public Type getAt(int x, int y) {
        return m[x][y];
    }

    public Matrix4<Type> setAt(int x, int y, Type value) throws ArrayIndexOutOfBoundsException {
        if (x < 0 || x > 3) throw new ArrayIndexOutOfBoundsException("x: " + x);
        if (y < 0 || y > 3) throw new ArrayIndexOutOfBoundsException("y: " + y);
        m[x][y] = value;
        return this;
    }

    public Type[][] getAll() {
        return m;
    }

    public void setAll(Type[][] m) throws IllegalArgumentException {
        if (m.length > 4) throw new IllegalArgumentException("lenght too big");
        if (m[0].length > 4) throw new IllegalArgumentException("lenght too big");
        if (m[1].length > 4) throw new IllegalArgumentException("lenght too big");
        if (m[2].length > 4) throw new IllegalArgumentException("lenght too big");
        if (m[3].length > 4) throw new IllegalArgumentException("lenght too big");
        this.m = m;
    }
}
