package com.example.polysample.z

import android.opengl.GLES30
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import com.a.a.objlib.entity.Camera
import com.a.a.objlib.entity.ObjModel
import com.a.a.objlib.math.Matrix4f
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import com.a.a.objlib.math.Vector3f


/**
 * Created by cuicui on 20/12/2017.
 */
class ObjRender(model: ObjModel) : GLSurfaceView.Renderer {

    val TAG = "ObjRender"
    //plane
    val FOV_Y = 60f
    val NEAR_CLIP = 0.1f
    val FAR_CLIP = 1000f

    // Camera position and orientation:
    var EYE = Vector3f(0f, 50f, -50f)
    var TARGET = Vector3f(0f, 0f, 10f)
    var UP = Vector3f(0f, 1f, 0f)

    var model : ObjModel
    val camera : Camera = Camera(EYE, TARGET, UP, FOV_Y, NEAR_CLIP, FAR_CLIP)

    init {
        this.model = model
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        camera.setViewPort(width, height)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        model.createShader()
        GLES30.glClearColor(0.4f, 0.15f, 0.15f, 1.0f)
        GLES30.glEnable(GLES30.GL_DEPTH_TEST)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT or GLES30.GL_DEPTH_BUFFER_BIT)
        model.render(Matrix4f(), camera.getViewMatrix(), camera.getProjectionMatrix())
    }
}