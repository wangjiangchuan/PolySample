package com.a.a.objlib.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.*
import java.util.*


/**
 * Created by cuicui on 19/12/2017.
 */
class Utils {
    companion object {
        private var TAG = "ObjUtils"

        fun getFileContent(path : String) : MutableList<String> {
            val objFile = File(path)
            objFile.useLines {
                lines ->
                return lines.toList().toMutableList()
            }
        }

        fun getAssetFileContent(fileName : String, applicationContext: Context) : MutableList<String> {
            applicationContext.assets.open(fileName).bufferedReader().useLines {
                lines ->
                return lines.toList().toMutableList()
            }
        }
    }
}