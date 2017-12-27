package com.a.a.objlib.utils

import com.a.a.objlib.gl.basic.TypesDefine
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

/**
 * Created by cuicui on 20/12/2017.
 */
class BufferUtils() : TypesDefine() {
    companion object {
        fun createFloatBuffer(length : Int) : FloatBuffer {
            val buffer = ByteBuffer
                    .allocateDirect(FLOAT_SIZE  * length)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
            buffer.position(0)
            return buffer
        }

        fun createIntBuffer(length: Int) : IntBuffer {
            val buffer = ByteBuffer
                    .allocateDirect(INT_SIZE  * length)
                    .order(ByteOrder.nativeOrder())
                    .asIntBuffer()
            buffer.position(0)
            return buffer
        }

        fun createFloatBufferWithData(data : FloatArray) : FloatBuffer {
            val buffer = createFloatBuffer(data.size)
            buffer.put(data)
            buffer.flip()
            return buffer
        }

        fun createInBufferWithData(data : IntArray) : IntBuffer {
            val buffer = createIntBuffer(data.size)
            buffer.put(data)
            buffer.flip()
            return buffer
        }
    }
}