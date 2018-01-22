package com.scorsi.gameengine;

import com.scorsi.gameengine.math.Matrix4f;
import com.scorsi.gameengine.math.Vector3f;

public class Transform {

    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    public Transform() {
        translation = new Vector3f();
        rotation = new Vector3f();
        scale = new Vector3f();
    }

    public Matrix4f getTransformation() {
        Matrix4f translationMatrix = new Matrix4f().initTranslation(translation.x, translation.y, translation.z);
        Matrix4f rotationMatrix = new Matrix4f().initRotation(rotation.x, rotation.y, rotation.z);
        Matrix4f scaleMatrix = new Matrix4f().initScale(scale.x, scale.y, scale.z);

        return translationMatrix.multiply(rotationMatrix.multiply(scaleMatrix));
    }

    public Vector3f getTranslation() {
        return translation;
    }

    public void setTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void setTranslation(float x, float y, float z) {
        this.translation = new Vector3f(x, y, z);
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation = new Vector3f(x, y, z);
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setScale(Vector3f scale) {
        this.scale = scale;
    }

    public void setScale(float x, float y, float z) {
        this.scale = new Vector3f(x, y, z);
    }

}
