package com.a.a.objlib.model

/**
 * Created by cuicui on 19/12/2017.
 */
class Face(vertexs : MutableList<Vertex>, materialName : String) {
    companion object {
        var INVALID_MATERIAL = ""
    }
    var matrialName : String = INVALID_MATERIAL
        private set
    var vertexs : MutableList<Vertex> = mutableListOf()
        private set

    init {
        this.matrialName = materialName
        this.vertexs = vertexs
    }
}