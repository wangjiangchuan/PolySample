package com.a.a.objlib.gl.attribute

import android.opengl.GLES30
import com.a.a.objlib.math.Matrix4f
import com.a.a.objlib.utils.BufferUtils



/**
 * Created by cuicui on 20/12/2017.
 */
class UniformMatrix(name: String) : Uniform(name) {

    fun loadMatrix(matrix: Matrix4f) {
        matrix.store(matrixBuffer)
        matrixBuffer.flip()
        GLES30.glUniformMatrix4fv(super.location, 1,false, matrixBuffer)
    }

    fun loadMatrix(matrix : FloatArray) {
        matrixBuffer.put(matrix)
        matrixBuffer.flip()
        GLES30.glUniformMatrix4fv(super.location, 1,false, matrixBuffer)
    }

    companion object {
        private val matrixBuffer = BufferUtils.createFloatBuffer(16)
    }
}
