package com.example.polysample.z

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import com.a.a.objlib.gl.`object`.Program
import com.a.a.objlib.gl.`object`.VAO
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Created by cuicui on 21/12/2017.
 */
class TestRender(vertexString : String, fragmentString: String) : GLSurfaceView.Renderer {
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
            0.0f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f
    )

    private val indices = intArrayOf(
            0, 1, 2
    )

    var vertexString = ""
    var fragmentString = ""

    var shaderProgram : Program? = null
    var vao : VAO? = null

    private val mVertices: FloatBuffer


    init {
        this.vertexString = vertexString
        this.fragmentString = fragmentString

        mVertices = ByteBuffer.allocateDirect(mVerticesData.size * 4)
                .order(ByteOrder.nativeOrder()).asFloatBuffer()
        mVertices.put(mVerticesData).position(0)
    }


    fun surfaceInit() {
        shaderProgram = Program(vertexString, fragmentString, arrayOf("aPosition"))
        vao = VAO.create()
        vao!!.bind()
        vao!!.createFloatAttribute(0, mVerticesData, 3)
        vao!!.createIndexBuffer(indices)
        vao!!.unbind()
        GLES30.glClearColor(0.0f, 1.0f, 1.0f, 1.0f)
    }

    fun drawFrame() {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT)
        shaderProgram!!.useProgram()
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT, false, 0, mVertices)
        GLES30.glEnableVertexAttribArray(0)
        GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, 3)
//        vao!!.bind(0)
//        GLES30.glDrawElements(GLES30.GL_TRIANGLES, vao!!.indexCount, GLES30.GL_UNSIGNED_INT, 0)
//        vao!!.unbind()
        shaderProgram!!.unuseProgram()
    }

}