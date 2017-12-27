package com.a.a.objlib.gl.attribute

import android.opengl.GLES30
import com.a.a.objlib.math.Vector4f


/**
 * Created by cuicui on 20/12/2017.
 */
class UniformVec4(name: String) : Uniform(name) {

    fun loadVec4(vector: Vector4f) {
        loadVec4(vector.x, vector.y, vector.z, vector.w)
    }

    fun loadVec4(x: Float, y: Float, z: Float, w: Float) {
        GLES30.glUniform4f(super.location, x, y, z, w)
    }

}