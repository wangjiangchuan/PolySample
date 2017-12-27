package com.a.a.objlib.math

/**
 * Created by cuicui on 20/12/2017.
 */
class Vector4f {
    var x : Float = 0f
    var y : Float = 0f
    var z : Float = 0f
    var w : Float = 0f

    constructor(x : Float, y : Float, z : Float, w : Float) {
        this.x = x
        this.y = y
        this.z = z
        this.w = w
    }

    constructor(other : Vector4f) {
        this.x = other.x
        this.y = other.y
        this.z = other.z
        this.w = other.w
    }

    constructor(value : Float) {
        this.x = value
        this.y = value
        this.z = value
        this.w = value
    }

    constructor() {
    }

    fun add(other : Vector4f) {
        this.x += other.x
        this.y += other.y
        this.z += other.z
    }

    fun add(params : Float) {
        this.x += params
        this.y += params
        this.z += params
        this.w += params
    }

    fun scale(scale : Float) {
        this.x *= scale
        this.y *= scale
        this.z *= scale
        this.w *= scale
    }

    fun dot(other : Vector4f) {
        this.x *= other.x
        this.y *= other.y
        this.z *= other.z
        this.w *= other.w
    }

    companion object {
        fun add(first: Vector4f, second : Vector4f) : Vector4f {
            return Vector4f(first.x + second.x, first.y + second.y, first.z + second.z, first.w + second.w)
        }

        fun dot(first: Vector4f, second: Vector4f) : Vector4f {
            return Vector4f(first.x * second.x, first.y * second.y, first.z * second.z, first.w * second.w)
        }
    }
}