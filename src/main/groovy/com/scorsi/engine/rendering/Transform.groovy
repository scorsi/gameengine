package com.scorsi.engine.rendering

import com.scorsi.engine.core.math.Matrix4f
import com.scorsi.engine.core.math.Quaternion
import com.scorsi.engine.core.math.Vector3f
import groovy.transform.CompileStatic
import groovy.transform.ToString

@CompileStatic
@ToString(includePackage = false, includeNames = true)
class Transform {

    Transform parent

    Vector3f translation
    Quaternion rotation
    Vector3f scale

    private Vector3f oldTranslation
    private Quaternion oldRotation
    private Vector3f oldScale

    private Matrix4f cachedTranslationMatrix
    private Matrix4f cachedRotationMatrix
    private Matrix4f cachedScaleMatrix

    private Matrix4f cachedTransformation

    Transform() {
        translation = new Vector3f()
        rotation = new Quaternion()
        scale = new Vector3f(1, 1, 1)

        oldTranslation = new Vector3f().set(translation)
        oldRotation = new Quaternion().set(rotation)
        oldScale = new Vector3f().set(scale)

        calculateTranslationMatrix()
        calculateRotationMatrix()
        calculateScaleMatrix()
        calculateTransformation()
    }

    Quaternion rotate(Vector3f axis, float angle) {
        rotation = (new Quaternion(axis, angle) * rotation).normalize()
    }

    Vector3f getTransformedTranslation()
    {
        if (parent)
            return parent.transformation.transform(translation)
        else
            return new Matrix4f().transform(translation)
    }

    Quaternion getTransformedRotation()
    {
        Quaternion parentRotation = new Quaternion(0,0,0,1)

        if(parent != null)
            parentRotation = parent.transformedRotation

        return parentRotation * rotation
    }

    Matrix4f getTransformation() {
//        Matrix4f translationMatrix = Matrix4f.translate(translation.x, translation.y, translation.z)
//        Matrix4f rotationMatrix = rotation.toRotationMatrix()
//        Matrix4f scaleMatrix = Matrix4f.scale(scale.getX(), scale.getY(), scale.getZ())
//
//        if (parent)
//            return parent.transformation * translationMatrix * rotationMatrix * scaleMatrix
//        else
//            return translationMatrix * rotationMatrix * scaleMatrix

        boolean hasChanged = false

        if (translation != oldTranslation) {
            oldTranslation.set(translation)
            calculateTranslationMatrix()
            hasChanged = true
        }
        if (rotation != oldRotation) {
            oldRotation.set(rotation)
            calculateRotationMatrix()
            hasChanged = true
        }
        if (scale != oldScale) {
            oldScale.set(scale)
            calculateScaleMatrix()
            hasChanged = true
        }

        if (hasChanged) {
            calculateTransformation()
        }

        if (parent)
            return parent.transformation * cachedTransformation
        else
            return cachedTransformation
    }

    private Matrix4f calculateTransformation() {
        cachedTransformation = cachedTranslationMatrix * cachedRotationMatrix * cachedScaleMatrix
    }

    private Matrix4f calculateTranslationMatrix() {
        cachedTranslationMatrix = Matrix4f.translate(translation.x, translation.y, translation.z)
    }

    private Matrix4f calculateRotationMatrix() {
        cachedRotationMatrix = rotation.toRotationMatrix()
    }

    private Matrix4f calculateScaleMatrix() {
        cachedScaleMatrix = Matrix4f.scale(scale.x, scale.y, scale.z)
    }

}
