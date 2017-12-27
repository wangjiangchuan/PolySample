package com.a.a.objlib.model

import com.a.a.objlib.math.Vector3f

/**
 * Created by cuicui on 20/12/2017.
 */
class Material(name: String) {
    // OpenGL ES Android Edition doesn't support material and LightMode, so there is no need to do this.
    // ambient reflectivity
//    var Ka : Vector3f = DEFAULT_COLOR
    // diffuse reflectivity
//    var Kd : Vector3f = DEFAULT_COLOR
    // specular reflectivity
//    var Ks : Vector3f = DEFAULT_COLOR
    // illumination level
//    var illum : Int = DEFAULT_ILLUMINATION_LEVEL

    var name = INVALID_NAME
    var color: Vector3f = DEFAULT_COLOR

    fun getColor(size : Int) : FloatArray {
        val result = mutableListOf<Float>()
        for (i in 0..size-1) {
            result.add(color.x)
            result.add(color.y)
            result.add(color.z)
            result.add(1.0f)
        }
        return result.toFloatArray()
    }

    init {
        this.name = name
    }

    companion object {
        var INVALID_NAME = ""
        var DEFAULT_COLOR = Vector3f(0f)

        fun parseMaterial(content : List<String>): Map<String, Material> {
            var currentMaterial: Material? = null
            val result = mutableMapOf<String, Material>()
            content.forEach {
                val indexOfSpace = it.indexOf(' ')
                val verb = if (indexOfSpace >= 0) it.substring(0, indexOfSpace) else it
                val args = if (indexOfSpace >= 0) it.substring(indexOfSpace+1) else it
                when (verb) {
                    "newmtl" -> {
                        currentMaterial = Material(args)
                        result.set(currentMaterial!!.name, currentMaterial!!)
                    }

                    "Kd" -> {
                        currentMaterial?.let {
                            val parts = args.split(" ")
                            if (parts.size == 0) throw RuntimeException("no color")
                            if (parts.size == 1) it.color = Vector3f(parts[0].toFloat())
                            if (parts.size == 2) throw throw RuntimeException("only two parameters in color")
                            if (parts.size == 3) it.color = Vector3f(parts[0].toFloat(), parts[1].toFloat(), parts[2].toFloat())
                        } ?: run {
                            throw RuntimeException("Error no material name specified")
                        }
                    }
                }
            }
            return result
        }
    }
}