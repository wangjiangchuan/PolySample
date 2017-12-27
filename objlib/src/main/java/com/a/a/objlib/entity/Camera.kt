package com.a.a.objlib.entity

import android.opengl.GLES30
import android.opengl.Matrix
import android.util.Log
import com.a.a.objlib.math.Matrix4f
import com.a.a.objlib.math.Vector3f

/**
 * Created by cuicui on 21/12/2017.
 */
class Camera {

    private var width = 0
    private var height = 0

    // FOR VIEW MATRIX
    var EYE_X = 0f; var EYE_Y = 0f; var EYE_Z = 0f;

    var TARGET_X = 0f; var TARGET_Y = 0f; var TARGET_Z = 0f;

    private var UP_X = 0f; private var UP_Y = 1f; private var UP_Z = 0f;

    private var viewMatrix = Matrix4f()
    private var isVMCalculated = false

    fun setTargetPos(pos : Vector3f) {
        TARGET_X = pos.x
        TARGET_Y = pos.y
        TARGET_Z = pos.z
        isVMCalculated = false
    }

    fun setUpDirect(pos : Vector3f) {
        UP_X = pos.x
        UP_Y = pos.y
        UP_Z = pos.z
        isVMCalculated = false
    }

    fun setEyePos(pos : Vector3f) {
        EYE_X = pos.x
        EYE_Y = pos.y
        EYE_Z = pos.z
        isVMCalculated = false
    }

    fun getViewMatrix() : Matrix4f {
        setLookAtM(viewMatrix,
                EYE_X, EYE_Y, EYE_Z,
                TARGET_X, TARGET_Y, TARGET_Z,
                UP_X, UP_Y, UP_Z)
        isVMCalculated = true
        return viewMatrix
    }

    // FOR PROJECTION MATRIX
    var FOV_Y = 60f;    var NEAR_CLIP = 0f;   var FAR_CLIP = 0f
    private var projectionMatrix = Matrix4f()
    private var isPMCalculated = false

    fun setViewPort(width : Int, height : Int) {
        this.width = width
        this.height = height
        isPMCalculated = false
        GLES30.glViewport(0, 0, width, height)
    }

    fun getProjectionMatrix() : Matrix4f {
        perspectiveM(projectionMatrix, FOV_Y, (width.toFloat()/ height.toFloat()), NEAR_CLIP, FAR_CLIP)
        isPMCalculated = true
        return projectionMatrix
    }

    fun getVPMatrix() : FloatArray {
        val result = FloatArray(16)
        if (isPMCalculated && isVMCalculated)
            Matrix.multiplyMM(result, 0, viewMatrix.toColumnArray(), 0, projectionMatrix.toColumnArray(), 0)
        else
            Matrix.multiplyMM(result, 0, getViewMatrix().toColumnArray(), 0, getProjectionMatrix().toColumnArray(), 0)
        return result
    }

    fun getMVPMatrix(model : Matrix4f) : FloatArray {
        var temp = FloatArray(16)
        var result = FloatArray(16)
        if (isVMCalculated)
            Matrix.multiplyMM(temp, 0, viewMatrix.toColumnArray(), 0, model.toColumnArray(), 0)
        else
            Matrix.multiplyMM(temp, 0, getViewMatrix().toColumnArray(), 0, model.toColumnArray(), 0)
        if (isPMCalculated)
            Matrix.multiplyMM(result, 0, projectionMatrix.toColumnArray(), 0, temp, 0)
        else
            Matrix.multiplyMM(result, 0, getProjectionMatrix().toColumnArray(), 0, temp, 0)
        return result
    }

    fun glStoreMVPMatrix() {

    }

    constructor() {}

    constructor(eye : Vector3f, target : Vector3f, up : Vector3f) {
        EYE_X = eye.x;      EYE_Y = eye.y;      EYE_Z = eye.z
        TARGET_X = target.x;TARGET_Y = target.y;TARGET_Z = target.z
        UP_X = up.x;        UP_Y = up.y;        UP_Z = up.z
    }

    constructor(fovy: Float, near_clip : Float, far_clip : Float) {
        FOV_Y = fovy;    NEAR_CLIP = near_clip; FAR_CLIP = far_clip
    }

    constructor(eye : Vector3f, target : Vector3f, up : Vector3f, fovy: Float, near_clip : Float, far_clip : Float) {
        EYE_X = eye.x;      EYE_Y = eye.y;      EYE_Z = eye.z
        TARGET_X = target.x;TARGET_Y = target.y;TARGET_Z = target.z
        UP_X = up.x;        UP_Y = up.y;        UP_Z = up.z
        FOV_Y = fovy;    NEAR_CLIP = near_clip; FAR_CLIP = far_clip
    }

    companion object {
        fun setLookAtM(rm: Matrix4f,
                       eyeX: Float, eyeY: Float, eyeZ: Float,
                       centerX: Float, centerY: Float, centerZ: Float, upX: Float, upY: Float,
                       upZ: Float) {

            // See the OpenGL GLUT documentation for gluLookAt for a description
            // of the algorithm. We implement it in a straightforward way:

            var fx = centerX - eyeX
            var fy = centerY - eyeY
            var fz = centerZ - eyeZ

            // Normalize f
            val rlf = 1.0f / Matrix.length(fx, fy, fz)
            fx *= rlf
            fy *= rlf
            fz *= rlf

            // compute s = f x up (x means "cross product")
            var sx = fy * upZ - fz * upY
            var sy = fz * upX - fx * upZ
            var sz = fx * upY - fy * upX

            // and normalize s
            val rls = 1.0f / Matrix.length(sx, sy, sz)
            sx *= rls
            sy *= rls
            sz *= rls

            // compute u = s x f
            val ux = sy * fz - sz * fy
            val uy = sz * fx - sx * fz
            val uz = sx * fy - sy * fx

            rm.m00 = sx
            rm.m10 = ux
            rm.m20= -fx
            rm.m30 = 0.0f

            rm.m01 = sy
            rm.m11 = uy
            rm.m21 = -fy
            rm.m31 = 0.0f

            rm.m02 = sz
            rm.m12 = uz
            rm.m22 = -fz
            rm.m32 = 0.0f

            rm.m03 = 0.0f
            rm.m13 = 0.0f
            rm.m23 = 0.0f
            rm.m33 = 1.0f

            rm.translateM(-eyeX, -eyeY, -eyeZ)
        }

        fun perspectiveM(m: Matrix4f,
                         fovy: Float, aspect: Float, zNear: Float, zFar: Float) {
            val f = 1.0f / Math.tan(fovy * (Math.PI / 360.0)).toFloat()
            val rangeReciprocal = 1.0f / (zNear - zFar)

            m.m00 = f / aspect
            m.m10 = 0.0f
            m.m20 = 0.0f
            m.m30 = 0.0f

            m.m01 = 0.0f
            m.m11 = f
            m.m21 = 0.0f
            m.m31 = 0.0f

            m.m02 = 0.0f
            m.m12 = 0.0f
            m.m22 = (zFar + zNear) * rangeReciprocal
            m.m32 = -1.0f

            m.m03= 0.0f
            m.m13 = 0.0f
            m.m23 = 2.0f * zFar * zNear * rangeReciprocal
            m.m33 = 0.0f
        }

        open fun checkIdentity(matrix1 : FloatArray, matrix2 : FloatArray) : Boolean {
            for (i in 0..15) {
                if (matrix1[i] != matrix2[i]) {
                    Log.e("wjc", "first " + matrix1[i] + " second " + matrix2[i])
                    return false
                }
            }
            return true
        }
    }
}