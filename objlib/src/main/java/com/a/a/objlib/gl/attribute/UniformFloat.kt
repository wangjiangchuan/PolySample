package com.a.a.objlib.gl.attribute

import android.opengl.GLES30

/**
 * Created by cuicui on 20/12/2017.
 */
class UniformFloat(name: String) : Uniform(name) {
    var currentValue : Float? = null
    var used = false

    fun loadFloat(value : Float) {
        if (!used || currentValue != value) {
            GLES30.glUniform1f(super.location, value)
            used = true
            currentValue = value
        }
    }
}