package com.a.a.objlib.gl.attribute

import android.opengl.GLES20.glUniform2f
import android.R.attr.y
import android.R.attr.x
import android.opengl.GLES30
import com.a.a.objlib.math.Vector2f


/**
 * Created by cuicui on 20/12/2017.
 */
class UniformVec2(name: String) : Uniform(name) {

    private var currentX: Float = 0f
    private var currentY: Float = 0f
    private var used = false

    fun loadVec2(vector: Vector2f) {
        loadVec2(vector.x, vector.y)
    }

    fun loadVec2(x: Float, y: Float) {
        if (!used || x != currentX || y != currentY) {
            this.currentX = x
            this.currentY = y
            used = true
            GLES30.glUniform2f(super.location, x, y)
        }
    }

}