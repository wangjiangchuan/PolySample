package com.a.a.objlib.gl.attribute

import android.opengl.GLES30
import com.a.a.objlib.math.Vector3f


/**
 * Created by cuicui on 20/12/2017.
 */

class UniformVec3(name: String) : Uniform(name) {

    private var currentX: Float = 0f
    private var currentY: Float = 0f
    private var currentZ: Float = 0f
    private var used = false

    fun loadVec3(vector: Vector3f) {
        loadVec3(vector.x, vector.y, vector.z)
    }

    fun loadVec3(x: Float, y: Float, z: Float) {
        if (!used || x != currentX || y != currentY || z != currentZ) {
            this.currentX = x
            this.currentY = y
            this.currentZ = z
            used = true
            GLES30.glUniform3f(super.location, x, y, z)
        }
    }
}