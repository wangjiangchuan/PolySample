package com.example.polysample.z

import android.graphics.Bitmap
import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.a.a.objlib.gl.`object`.Program
import com.a.a.objlib.gl.`object`.VAO
import com.a.a.objlib.gl.texture.Texture
import com.a.a.objlib.gl.texture.TextureBuilder
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by cuicui on 21/12/2017.
 */
class TestRender(vertexString : String, fragmentString: String, bitmap: Bitmap) : GLSurfaceView.Renderer {
    override fun onDrawFrame(gl: GL10?) {
        drawFrame()
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES30.glViewport(0, 0, width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        surfaceInit()
    }

    private val TAG = "HelloTriangleRenderer"

    private val mVerticesData = floatArrayOf(
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f
    )

    private val indices = intArrayOf(
            0, 1, 2,
            0, 2, 3
    )

    private val texCoords = floatArrayOf(
            0f, 0f,
            0f, 1f,
            1f, 1f,
            1f, 0f
    )

    var vertexString = ""
    var fragmentString = ""

    var shaderProgram : Program? = null
    var vao : VAO? = null
    var texture : Texture? = null

    private val mVertices: FloatBuffer
    private val bitmap : Bitmap


    init {
        this.vertexString = vertexString
        this.fragmentString = fragmentString

        mVertices = ByteBuffer.allocateDirect(mVerticesData.size * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertices.put(mVerticesData).position(0)
        this.bitmap = bitmap

    }

    fun surfaceInit() {
        shaderProgram = Program(vertexString, fragmentString, arrayOf("aPosition", "aTexCoord"))
        vao = VAO.create()
        vao!!.bind()
        vao!!.createFloatAttribute(0, mVerticesData, 3)
        vao!!.createFloatAttribute(1, texCoords, 2)
        vao!!.createIndexBuffer(indices)
        vao!!.unbind()
        texture = Texture.newTexture(bitmap).anisotropic().create()
        GLES30.glClearColor(0.0f, 1.0f, 1.0f, 1.0f)
    }

    fun drawFrame() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        shaderProgram!!.useProgram()
        vao!!.bind(intArrayOf(0, 1))
        texture!!.bind(0)
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, vao!!.indexCount, GLES30.GL_UNSIGNED_INT, 0)
        vao!!.unbind()
        shaderProgram!!.unuseProgram()
    }

}