package com.a.a.objlib.gl.texture

import android.graphics.Bitmap
import android.opengl.GLES30
import android.opengl.GLUtils
import android.text.style.TypefaceSpan
import com.a.a.objlib.gl.basic.TypesDefine
import javax.microedition.khronos.opengles.GL11



/**
 * Created by cuicui on 27/12/2017.
 */
class Texture : TypesDefine {

    var id : Int
    var type : Int

    constructor(id : Int) {
        this.id = id
        this.type = GL11.GL_TEXTURE_2D;
    }

    constructor(id : Int, type: Int) {
        this.id = id
        this.type = type
    }

    fun bind(uint : Int) {
        GLES30.glActiveTexture(GLES30.GL_TEXTURE0 + uint);
        GLES30.glBindTexture(type, id);
    }

    fun delete() {
        GLES30.glDeleteTextures(1, IntArray(id), 0)
    }

    companion object {
        fun newTexture(bitmap: Bitmap) : TextureBuilder {
            return TextureBuilder(bitmap)
        }
    }
}