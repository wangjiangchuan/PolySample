package com.a.a.objlib.gl.`object`

import android.opengl.GLES30
import com.a.a.objlib.gl.basic.TypesDefine
import com.a.a.objlib.utils.BufferUtils
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by cuicui on 19/12/2017.
 */
class VBO(id : Int, type : Int) : TypesDefine() {

    companion object {
        open fun create(type: Int) : VBO {
            val vboId = IntArray(1)
            GLES30.glGenBuffers(1, vboId, 0)
            return VBO(vboId[0], type)
        }
    }

    var vboId : Int = INVALID_ID
    var type : Int = INVALID_TYPE         // 类型 GL_ARRAY_BUFFER GL_ELEMENT_ARRAY_BUFFER

    init {
        this.vboId = id
        this.type = type
    }

    fun bind() {
        GLES30.glBindBuffer(type, vboId)
    }

    fun unbind() {
        GLES30.glBindBuffer(type, 0)
    }

    fun storeData(data : FloatArray) {
        var buffer = BufferUtils.createFloatBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        storeData(buffer)
    }

    fun storeData(data : FloatBuffer) {
        GLES30.glBufferData(type, data.capacity() * FLOAT_SIZE, data, GLES30.GL_STATIC_DRAW)
    }

    fun storeData(data : IntArray) {
        val buffer = BufferUtils.createIntBuffer(data.size)
        buffer.put(data)
        buffer.flip()
        storeData(buffer)
    }

    fun storeData(data : IntBuffer) {
        GLES30.glBufferData(type, data.capacity() * INT_SIZE, data, GLES30.GL_STATIC_DRAW);
    }

    fun delete() {
        GLES30.glDeleteBuffers(1, IntArray(vboId), 0)
    }
}