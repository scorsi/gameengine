package com.scorsi.gameengine.utils;

public abstract class Vector2<Type> {

    protected Type x;
    protected Type y;

    public Vector2() {
    }

    public Vector2(Type x, Type y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2<Type> other) {
        this.x = other.getX();
        this.y = other.getY();
    }

    public abstract Type length();

    public abstract Type dot(Vector2<Type> r);

    public abstract Vector2<Type> normalize();

    public abstract Vector2<Float> rotate(Float angle);

    public abstract Vector2<Type> add(Vector2<Type> r);

    public abstract Vector2<Type> add(Type r);

    public abstract Vector2<Type> sub(Vector2<Type> r);

    public abstract Vector2<Type> sub(Type r);

    public abstract Vector2<Type> mul(Vector2<Type> r);

    public abstract Vector2<Type> mul(Type r);

    public abstract Vector2<Type> div(Vector2<Type> r);

    public abstract Vector2<Type> div(Type r);

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

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
