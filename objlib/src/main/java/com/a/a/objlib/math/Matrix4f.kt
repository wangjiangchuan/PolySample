package com.a.a.objlib.math

import java.nio.FloatBuffer
import java.util.*

/**
 * Created by cuicui on 20/12/2017.
 */
class Matrix4f() {

    var m00:Float = 0f; var m01:Float = 0f; var m02:Float = 0f; var m03:Float = 0f;
    var m10:Float = 0f; var m11:Float = 0f; var m12:Float = 0f; var m13:Float = 0f;
    var m20:Float = 0f; var m21:Float = 0f; var m22:Float = 0f; var m23:Float = 0f;
    var m30:Float = 0f; var m31:Float = 0f; var m32:Float = 0f; var m33:Float = 0f;

    init {
        m00 = 1f
        m11 = 1f
        m22 = 1f
        m33 = 1f
    }

    fun setIdentity() {
        m00 = 1.0f;m01 = 0.0f;m02 = 0.0f;m03 = 0.0f;
        m10 = 0.0f;m11 = 1.0f;m12 = 0.0f;m13 = 0.0f;
        m20 = 0.0f;m21 = 0.0f;m22 = 1.0f;m23 = 0.0f;
        m30 = 0.0f;m31 = 0.0f;m32 = 0.0f;m33 = 1.0f
    }

    fun store(buf: FloatBuffer): Matrix4f {
        buf.put(toColumnArray())
        return this
    }

    fun translateM(x : Float, y : Float, z : Float) {
        m03 = m00 * x + m01 * y + m02 * z
        m13 = m10 * x + m11 * y + m12 * z
        m23 = m20 * x + m21 * y + m22 * z
    }

    fun toColumnArray() : FloatArray {
        return floatArrayOf(
                m00, m10, m20, m30,
                m01, m11, m21, m31,
                m02, m12, m22, m32,
                m03, m13, m23, m33
        )
    }

    fun toRowArray() : FloatArray {
        return floatArrayOf(
                m00, m01, m02, m03,
                m10, m11, m12, m13,
                m20, m21, m22, m23,
                m30, m31, m32, m33
        )
    }

    fun scale(target : Vector3f) : Matrix4f {
        m00 *= target.x
        m01 *= target.y
        m02 *= target.z

        m10 *= target.x
        m11 *= target.y
        m12 *= target.z

        m20 *= target.x
        m21 *= target.y
        m22 *= target.z

        m30 *= target.x
        m31 *= target.y
        m32 *= target.z
        return this
    }

    @Synchronized fun rotate(angle : Float, direction : Vector3f) : Matrix4f {
        var a : Float = angle * (Math.PI / 180.0f).toFloat()
        val s = Math.sin(a.toDouble()).toFloat()
        val c = Math.cos(a.toDouble()).toFloat()
        if (1.0f == direction.x && 0.0f == direction.y && 0.0f == direction.z) {
            m11 = c;   m22= c
            m21 = s;   m12 = -s
            m10 = 0f;  m20 = 0f
            m01 = 0f;  m02 = 0f
            m00 = 1f
        } else if (0.0f == direction.x && 1.0f == direction.y && 0.0f == direction.z) {
            m00 = c;   m22 = c
            m02 = s;   m20 = -s
            m10 = 0f;  m01 = 0f
            m21 = 0f;  m12 = 0f
            m11 = 1f
        } else if (0.0f == direction.x && .0f == direction.y && 1.0f == direction.z) {
            m00 = c;     m11 = c
            m10 = s;     m10 = -s
            m20 = 0f;    m21 = 0f
            m02 = 0f;    m12 = 0f
            m22 = 1f
        } else {
            val temp = Vector3f(direction)
            val len = length(temp.x, temp.y, temp.z)
            if (1.0f != len) {
                val recipLen = 1.0f / len
                temp.x *= recipLen
                temp.y *= recipLen
                temp.z *= recipLen
            }
            val nc = 1.0f - c
            val xy = temp.x * temp.y
            val yz = temp.y * temp.z
            val zx = temp.z * temp.x
            val xs = temp.x * s
            val ys = temp.y * s
            val zs = temp.z * s
            m00 = temp.x * temp.x * nc + c
            m01 = xy * nc - zs
            m02 = zx * nc + ys
            m10 = xy * nc + zs
            m11 = temp.y * temp.y * nc + c
            m12 = yz * nc - xs
            m20 = zx * nc - ys
            m21 = yz * nc + xs
            m22 = temp.z * temp.z * nc + c
        }
        
        return this
    }

    fun translate(target: Vector3f) : Matrix4f {
        m03 *= m00 * target.x + m01 * target.y + m02 * target.z + m03
        m13 *= m10 * target.x + m11 * target.y + m12 * target.z + m13
        m23 *= m20 * target.x + m21 * target.y + m22 * target.z + m23
        m33 *= m30 * target.x + m31 * target.y + m32 * target.z + m33
        return this
    }

    companion object {
        fun setIdentity(m: Matrix4f): Matrix4f {
            m.m00 = 1.0f;m.m01 = 0.0f;m.m02 = 0.0f;m.m03 = 0.0f;
            m.m10 = 0.0f;m.m11 = 1.0f;m.m12 = 0.0f;m.m13 = 0.0f;
            m.m20 = 0.0f;m.m21 = 0.0f;m.m22 = 1.0f;m.m23 = 0.0f;
            m.m30 = 0.0f;m.m31 = 0.0f;m.m32 = 0.0f;m.m33 = 1.0f
            return m
        }

        fun length(x: Float, y: Float, z: Float): Float {
            return Math.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
        }
    }
}