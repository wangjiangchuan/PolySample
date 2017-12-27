package com.a.a.objlib.gl.attribute

import com.a.a.objlib.math.Matrix4f
import com.a.a.objlib.math.Vector3f

/**
 * Created by cuicui on 20/12/2017.
 */
class UniformMat4Array(name: String, size: Int) : Uniform(name) {

    var matrixUniforms : Array<UniformMatrix>? = null

    fun UniformMat4Array(name: String, size: Int) {
        matrixUniforms = Array(size, init = {
            index : Int ->
            UniformMatrix(name + "[" + index + "]")
        })
    }

    override fun storeUniformLocation(programID: Int) {
        matrixUniforms?.forEach {
            it.storeUniformLocation(programID)
        }
    }

    fun loadMatrixArray(matrices: Array<Matrix4f>) {
        matrices.forEachIndexed {
            index, matrix4f ->
            matrixUniforms!![index].loadMatrix(matrix4f)
        }
    }
}