package com.scorsi.gameengine.utils;

public class Vector2f extends Vector2<Float> {

    public Vector2f() {
    }

    public Vector2f(Float x, Float y) {
        super(x, y);
    }

    public Vector2f(Vector2<Float> other) {
        super(other);
    }

    public Float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    public Float dot(Vector2<Float> r) {
        return x * r.getX() + y * r.getY();
    }

    public Vector2f normalize() {
        float length = this.length();
        x /= length;
        y /= length;
        return this;
    }

    public Vector2f rotate(Float angle) {
        double rad = Math.toRadians(angle);
        double cos = Math.cos(rad);
        double sin = Math.sin(rad);

        return new Vector2f((float) (x * cos - y * sin), (float) (x * sin + y * cos));
    }

    public Vector2f add(Vector2<Float> r) {
        return new Vector2f(x + r.getX(), y + r.getY());
    }

    public Vector2f add(Float r) {
        return new Vector2f(x + r, y + r);
    }

    public Vector2f sub(Vector2<Float> r) {
        return new Vector2f(x - r.getX(), y - r.getY());
    }

    public Vector2f sub(Float r) {
        return new Vector2f(x - r, y - r);
    }

    public Vector2f mul(Vector2<Float> r) {
        return new Vector2f(x * r.getX(), y * r.getY());
    }

    public Vector2f mul(Float r) {
        return new Vector2f(x * r, y * r);
    }

    public Vector2f div(Vector2<Float> r) {
        return new Vector2f(x / r.getX(), y / r.getY());
    }

    public Vector2f div(Float r) {
        return new Vector2f(x / r, y / r);
    }

}
