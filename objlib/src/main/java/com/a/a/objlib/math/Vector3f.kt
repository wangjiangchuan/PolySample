package com.a.a.objlib.math

/**
 * Created by cuicui on 19/12/2017.
 */
class Vector3f {
    var x : Float = 0f
    var y : Float = 0f
    var z : Float = 0f

    constructor(x : Float, y : Float, z : Float) {
        this.x = x
        this.y = y
        this.z = z
    }

    constructor(other : Vector3f) {
        this.x = other.x
        this.y = other.y
        this.z = other.z
    }

    constructor(value : Float) {
        this.x = value
        this.y = value
        this.z = value
    }

    constructor() {
        x = 0f
        y = 0f
        z = 0f
    }

    fun reverse() : Vector3f {
        return Vector3f(-x, -y, -z)
    }

    fun add(other : Vector3f) {
        this.x += other.x
        this.y += other.y
        this.z += other.z
    }

    fun add(params : Float) {
        this.x += params
        this.y += params
        this.z += params
    }

    fun scale(scale : Float) {
        this.x *= scale
        this.y *= scale
        this.z *= scale
    }

    fun dot(other : Vector3f) {
        this.x *= other.x
        this.y *= other.y
        this.z *= other.z
    }

    companion object {
        fun add(first: Vector3f, second : Vector3f) : Vector3f {
            return Vector3f(first.x + second.x, first.y + second.y, first.z + second.z)
        }

        fun dot(first: Vector3f, second: Vector3f) : Vector3f {
            return Vector3f(first.x * second.x, first.y * second.y, first.z * second.z)
        }
    }
}