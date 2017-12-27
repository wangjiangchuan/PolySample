package com.a.a.objlib.model

import com.a.a.objlib.math.Vector3f

/**
 * Created by cuicui on 20/12/2017.
 */
class Vertex {
    companion object {
        var MISSING_VALUE = -1
    }
    var vertexIndex : Int = MISSING_VALUE
        private set
    var textCoordIndex : Int = MISSING_VALUE
        private set
    var normalIndex : Int = MISSING_VALUE
        private set

    var vertex : Vector3f? = null
        private set

    constructor(vertexIndex : Int, texCoordIndex : Int, normalIndex : Int, vertex: Vector3f) {
        this.vertexIndex = vertexIndex
        this.textCoordIndex = texCoordIndex
        this.normalIndex = normalIndex
        this.vertex = vertex
    }

    constructor(other : Vertex) {
        this.vertexIndex = other.vertexIndex
        this.textCoordIndex = other.textCoordIndex
        this.normalIndex = other.normalIndex
    }

    fun isValid() : Boolean {
        if (vertexIndex != MISSING_VALUE && textCoordIndex != MISSING_VALUE && normalIndex != MISSING_VALUE) {
            return true
        }
        return false
    }
}