package com.scorsi.gameengine.utils;

public class Matrix4f extends Matrix4<Float> {

    Matrix4f() {
        m = new Float[4][4];
    }

    @Override
    public Matrix4f initIdentity() {
        m[0][0] = 1f;   m[0][1] = 0f;   m[0][2] = 0f;   m[0][3] = 0f;
        m[1][0] = 0f;   m[1][1] = 1f;   m[1][2] = 0f;   m[1][3] = 0f;
        m[2][0] = 0f;   m[2][1] = 0f;   m[2][2] = 1f;   m[2][3] = 0f;
        m[3][0] = 0f;   m[3][1] = 0f;   m[3][2] = 0f;   m[3][3] = 1f;
        return this;
    }

    @Override
    public Matrix4f mul(Matrix4<Float> r) {
        Matrix4f res = new Matrix4f();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                res.setAt(x, y, m[x][0] * r.getAt(0, y) +
                        m[x][1] * r.getAt(1, y) +
                        m[x][2] * r.getAt(2, y) +
                        m[x][3] * r.getAt(3, y));
            }
        }
        return null;
    }

}
