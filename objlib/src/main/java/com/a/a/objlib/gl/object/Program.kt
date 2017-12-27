package com.a.a.objlib.gl.`object`

import android.opengl.GLES30
import android.util.Log
import com.a.a.objlib.gl.basic.TypesDefine
import com.a.a.objlib.gl.attribute.Uniform





/**
 * Created by cuicui on 19/12/2017.
 */
class Program : TypesDefine {

    companion object {
        var TAG = "Program"

        open fun checkError() : Boolean {
            if (GLES30.glGetError() != GLES30.GL_NO_ERROR) {
                return false
            }
            return true
        }

        open fun loadShader(content : String, type : Int) : Int {
            val shaderID = GLES30.glCreateShader(type)
            GLES30.glShaderSource(shaderID, content)
            Log.e(TAG, if (type == GLES30.GL_VERTEX_SHADER) "vertex shader " + content else "fragment shader " + content)
            GLES30.glCompileShader(shaderID)
            if (!checkError()) {
                Log.e(TAG, "ERROR OCCUR " + GLES30.glGetShaderInfoLog(shaderID))
                throw RuntimeException()
            }
            return shaderID
        }
    }
    var programID = INVALID_ID

    constructor(vertexString : String, fragmentString : String, inVariables : Array<String>) {
        createProgram(vertexString, fragmentString)
        bindAttributes(inVariables)
    }

//    constructor(vertexPath : String, fragmentPath : String, type : Int, inVariables : Array<String>) {
//        var vertexString = Utils.getFileContent(vertexPath)
//        var fragmentString = Utils.getFileContent(fragmentPath)
//        Log.e(TAG, "vertex shader " + vertexString + fragmentString)
//        createProgram(vertexString, fragmentPath)
//    }

    private fun createProgram(vertexString : String, fragmentString : String) {
        val vertexShader = loadShader(vertexString, GLES30.GL_VERTEX_SHADER)
        val fragmentShader = loadShader(fragmentString, GLES30.GL_FRAGMENT_SHADER)
        programID = GLES30.glCreateProgram()
        GLES30.glAttachShader(programID, vertexShader)
        GLES30.glAttachShader(programID, fragmentShader)
        GLES30.glLinkProgram(programID)
        if (!checkError()) {
            Log.e(TAG, GLES30.glGetProgramInfoLog(programID))
            throw RuntimeException()
        }
        GLES30.glValidateProgram(programID)

    }

    fun bindAttributes(inVariables : Array<String>) {
        inVariables.forEachIndexed {
            index, s ->
            GLES30.glBindAttribLocation(programID, index, s)
        }
    }

    fun storeAllUniformLocations(vararg uniforms: Uniform) {
        uniforms.forEach {
            it.storeUniformLocation(programID);
        }
        GLES30.glValidateProgram(programID)
    }

    fun getPragramID(): Int {
        return programID
    }

    fun start() {
        GLES30.glUseProgram(programID)
    }

    fun stop() {
        GLES30.glUseProgram(0)
    }

    fun useProgram() {
        GLES30.glUseProgram(programID)
    }

    fun unuseProgram() {
        GLES30.glUseProgram(0)
    }

    fun cleanUp() {
        GLES30.glDeleteProgram(programID)
    }
}