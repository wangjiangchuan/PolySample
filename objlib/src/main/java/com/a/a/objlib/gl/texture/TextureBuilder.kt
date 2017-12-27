package com.a.a.objlib.gl.texture

import android.graphics.Bitmap
import android.opengl.ETC1.getWidth
import android.opengl.GLES30


/**
 * Created by cuicui on 27/12/2017.
 */
class TextureBuilder constructor(bitmap: Bitmap) {

    var isClampEdges = false
    var isMipmap = false
    var isAnisotropic = true
    var isNearest = false

    private var bitmap: Bitmap

    init {
        this.bitmap = bitmap
    }

    fun create(): Texture {
        val id = TextureUtils.loadBitmapToGL(bitmap, this)
        return Texture(id)
    }

    fun clampEdges(): TextureBuilder {
        this.isClampEdges = true
        return this
    }

    fun normalMipMap(): TextureBuilder {
        this.isMipmap = true
        this.isAnisotropic = false
        return this
    }

    fun nearestFiltering(): TextureBuilder {
        this.isMipmap = false
        this.isAnisotropic = false
        this.isNearest = true
        return this
    }

    fun anisotropic(): TextureBuilder {
        this.isMipmap = true
        this.isAnisotropic = true
        return this
    }

}