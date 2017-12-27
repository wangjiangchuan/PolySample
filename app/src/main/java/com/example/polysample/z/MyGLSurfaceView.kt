package com.example.polysample.z

import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.util.Log
import com.a.a.objlib.entity.ObjModel
import com.a.a.objlib.model.RawModel
import com.example.polysample.R
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import javax.microedition.khronos.egl.EGL10
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.egl.EGLContext
import javax.microedition.khronos.egl.EGLDisplay
import android.content.pm.ConfigurationInfo
import android.app.ActivityManager
import android.graphics.BitmapFactory
import com.a.a.objlib.model.Material
import com.a.a.objlib.utils.Utils
import com.example.polysample.origin.HelloTriangleRenderer


/**
 * Created by cuicui on 20/12/2017.
 */
class MyGLSurfaceView : GLSurfaceView {
    // The renderer responsible for rendering the contents of this view.
    val renderer: HelloTriangleRenderer
    var renderer2 : TestRender
    var renderer3 : ObjRender? = null
    constructor(context: Context) : this(context, null) {}

    constructor(context: Context, attributeSet: AttributeSet? = null) : super(context, attributeSet) {
        if (detectOpenGLES30(context)) {
            setEGLContextClientVersion(3)
        } else {
            throw RuntimeException()
        }

        var bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.diurnal_front)

        val vertex = readTextFile(context, R.raw.objvertexshader)
        val fragment = readTextFile(context, R.raw.objfragmentshader)
//        val materials = Material.parseMaterial(Utils.getAssetFileContent("obj.mtl", context.applicationContext))
//        val rawmodel : RawModel = RawModel.parseObj(Utils.getAssetFileContent("test.obj", context.applicationContext))

//        renderer3 = ObjRender(ObjModel(rawmodel, vertex, fragment, materials))

        renderer = HelloTriangleRenderer(context)
        renderer2 = TestRender(readTextFile(context, R.raw.testvertex), readTextFile(context, R.raw.testfragment), bitmap)
        setRenderer(renderer2)
    }

    companion object {

        private fun detectOpenGLES30(context: Context): Boolean {
            val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            val info = am.deviceConfigurationInfo
            return info.reqGlEsVersion >= 0x30000
        }

        fun readTextFile(ctx: Context, resId: Int): String {
            val inputStream = ctx.resources.openRawResource(resId)

            val inputreader = InputStreamReader(inputStream)
            val bufferedreader = BufferedReader(inputreader)
            var line: String? = bufferedreader.readLine()
            val stringBuilder = StringBuilder()
            try {
                while (line != null) {
                    stringBuilder.append(line)
                    stringBuilder.append('\n')
                    line = bufferedreader.readLine()
                }
            } catch (e: IOException) {
                throw RuntimeException()
            }

            return stringBuilder.toString()
        }
    }

}