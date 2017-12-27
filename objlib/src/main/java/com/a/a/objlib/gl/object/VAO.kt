package com.a.a.objlib.gl.`object`

import android.opengl.GLES30
import com.a.a.objlib.gl.basic.TypesDefine

/**
 * Created by cuicui on 19/12/2017.
 */
class VAO(id : Int) : TypesDefine() {

    companion object {
        fun create() : VAO {
            val id = IntArray(1)
            GLES30.glGenVertexArrays(1, id, 0)
            return VAO(id[0])
        }
    }

    var id = INVALID_ID
    var dataVBOs = mutableListOf<VBO>()
    var indexVBO : VBO? = null
    var indexCount : Int = INVALID_VALUE

    init {
        this.id = id
    }

    fun bind(attribute : Int) {
        bind()
        GLES30.glEnableVertexAttribArray(attribute)
    }

    fun bind(attributes : IntArray) {
        bind()
        attributes.forEach {
            GLES30.glEnableVertexAttribArray(it)
        }
    }

    fun bind() {
        GLES30.glBindVertexArray(id)
    }

    fun unbind(attributes : IntArray) {
        attributes.forEach {
            GLES30.glDisableVertexAttribArray(it)
        }
        unbind()
    }

    fun unbind() {
        GLES30.glBindVertexArray(0)
    }

    fun createIndexBuffer(indices : IntArray) {
        this.indexVBO = VBO.create(GLES30.GL_ELEMENT_ARRAY_BUFFER)
        indexVBO!!.bind()
        indexVBO!!.storeData(indices)
        this.indexCount = indices.size
    }

    fun createFloatAttribute(attributes : Int, data : FloatArray, attrsize : Int) {
        val dataVBO = VBO.create(GLES30.GL_ARRAY_BUFFER)
        dataVBO.bind()
        dataVBO.storeData(data)
        GLES30.glVertexAttribPointer(attributes, attrsize, GLES30.GL_FLOAT, false, attrsize * FLOAT_SIZE, 0)
        dataVBO.unbind()
        dataVBOs.add(dataVBO)
    }

    fun createIntAttribute(attribute : Int, data : IntArray, attrsize : Int) {
        val dataVBO = VBO.create(GLES30.GL_ARRAY_BUFFER)
        dataVBO.bind()
        dataVBO.storeData(data)
        GLES30.glVertexAttribPointer(attribute, attrsize, GLES30.GL_INT, false, attrsize * INT_SIZE, 0)
        dataVBO.unbind()
        dataVBOs.add(dataVBO)
    }

    fun bindVBOs() {
        dataVBOs.forEach {
            it.bind()
        }
    }

    fun unbindVBOs() {
        dataVBOs.forEach {
            it.unbind()
        }
    }

    fun delete() {
        GLES30.glDeleteVertexArrays(1, IntArray(id), 0)
        dataVBOs.forEach {
            it.delete()
        }
        indexVBO!!.delete()
    }
}