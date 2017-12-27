package com.example.polysample.z

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.polysample.R

/**
 * Created by cuicui on 20/12/2017.
 */

class TestActivity : AppCompatActivity() {

    private var glView: MyGLSurfaceView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_layout)
        glView = findViewById(R.id.my_gl_surface_view) as MyGLSurfaceView
    }
}