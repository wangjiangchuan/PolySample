package com.a.a.objlib.gl.attribute

import android.opengl.GLES30
import android.util.Log

/**
 * Created by cuicui on 20/12/2017.
 */
open class Uniform(name : String) {
    companion object {
        private var TAG = "Uniform"
        open val NOT_FOUND = -1
        open val INVALID_NAME = ""
        open val INVALID_VALUE = -1
    }

    var name = INVALID_NAME
    var location : Int = INVALID_VALUE
        private set

    init {
        this.name = name
    }

    open fun storeUniformLocation(programID : Int) {
        location = GLES30.glGetUniformLocation(programID, name)
        if (location == NOT_FOUND) {
            Log.e("TAG", "no uniform variable found with name " + this.name + "!")
        }
    }
}