package com.a.a.objlib.math

/**
 * Created by cuicui on 19/12/2017.
 */
class Vector2f {
    var x : Float = 0f
        private set
    var y : Float = 0f
        private set

    constructor(x : Float, y : Float) {
        this.x = x
        this.y = y
    }

    constructor(other : Vector2f) {
        this.x = other.x
        this.y = other.y
    }

    constructor() {
        x = 0f
        y = 0f
    }

    fun add(other : Vector2f) {
        this.x += other.x
        this.y += other.y
    }

    fun add(params : Float) {
        this.x += params
        this.y += params
    }

    fun scale(scale : Float) {
        this.x *= scale
        this.y *= scale
    }

    fun dot(other : Vector2f) {
        this.x *= other.x
        this.y *= other.y
    }

    companion object {
        fun add(first: Vector2f, second : Vector2f) : Vector2f {
            return Vector2f(first.x + second.x, first.y + second.y)
        }

        fun dot(first: Vector2f, second: Vector2f) : Vector2f {
            return Vector2f(first.x * second.x, first.y * second.y)
        }
    }
}