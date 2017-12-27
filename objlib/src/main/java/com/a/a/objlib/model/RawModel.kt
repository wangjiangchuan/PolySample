package com.a.a.objlib.model

import android.util.Log
import com.a.a.objlib.math.Vector2f
import com.a.a.objlib.math.Vector3f

/**
 * Created by cuicui on 19/12/2017.
 */
class RawModel() {
    // all data store in sections
    var verticeSection = arrayListOf<MutableList<Vector3f>>()
        private set

    var normalSection = arrayListOf<MutableList<Vector3f>>()
        private set

    var texCoordSection = arrayListOf<MutableList<Vector2f>>()
        private set

    var faceSection = arrayListOf<MutableList<Face>>()
        private set

    var indicesSection = arrayListOf<IntArray>()
        private set

    var verticeFloatSection = arrayListOf<FloatArray>()
        private set

    var totalIndices = 0
    private set
    var totalVertex = 0
    private set

    //bounds
    var boundsMin = Vector3f(0f)
    var boundsMax = Vector3f(0f)

    fun encapsulateInBounds(vertex : Vector3f) {
        boundsMin.x = Math.min(vertex.x, boundsMin.x)
        boundsMin.y = Math.min(vertex.y, boundsMin.y)
        boundsMin.z = Math.min(vertex.z, boundsMin.z)

        boundsMax.x = Math.max(vertex.x, boundsMax.x)
        boundsMax.y = Math.max(vertex.y, boundsMax.y)
        boundsMax.z = Math.max(vertex.z, boundsMax.z)
    }

    fun getBoundSize() : Vector3f {
        return Vector3f(boundsMax.x - boundsMin.x, boundsMax.y - boundsMin.y,
                boundsMax.z - boundsMin.z)
    }

    fun getBoundsCenter(): Vector3f {
        return Vector3f((boundsMin.x + boundsMax.x) / 2f, (boundsMin.y + boundsMax.y) / 2f,
                (boundsMin.z + boundsMax.z) / 2f)
    }

    private fun vec3ToArray(list : MutableList<Vector3f>, index : Int) {
        val result = arrayListOf<Float>()
        list.forEach {
            result.add(it.x)
            result.add(it.y)
            result.add(it.z)
        }
        this.verticeFloatSection.add(result.toFloatArray())
    }

    companion object {
        private var TAG = "RawObj"

        class ObjParseException(message: String, cause: Exception?) : Exception(message, cause)

        var toFloat = {
            s : String -> s.toFloat()
        }

        @Throws
        private fun tryParseInt(s : String): Int {
            return Integer.parseInt(s)
        }

        private fun parseFloatVec3(s : String) : Vector3f {
            val parts = s.trim().split(" ")
            if (parts.size != 3) throw ObjParseException("Vector3f doesn't have 3 components.", RuntimeException())
            return Vector3f(toFloat(parts[0]), toFloat(parts[1]), toFloat(parts[2]))
        }

        private fun parseFloatVec2(s : String) : Vector2f {
            val parts = s.trim().split(" ")
            if (parts.size < 2)
                throw ObjParseException("Vector2f doesn't have 2 components.", RuntimeException())
            return Vector2f(toFloat(parts[0]), toFloat(parts[1]))
        }

        private fun encapsulateInBounds(vertex : Vector3f, min : Vector3f, max : Vector3f) {
            min.x = Math.min(vertex.x, min.x)
            min.y = Math.min(vertex.y, min.y)
            min.z = Math.min(vertex.z, min.z)

            max.x = Math.max(vertex.x, max.x)
            max.y = Math.max(vertex.y, max.y)
            max.z = Math.max(vertex.z, max.z)
        }

        private fun parseFace(s : String, indices : ArrayList<Int>, vertices : MutableList<Vector3f>, material: String, currentIndex : Int, obj : RawModel) : Face {
            val list = mutableListOf<Vertex>()
            val parts = s.trim().split(" ")
            if (parts.size < 2) throw ObjParseException("Face must have at least 3 vertices.", RuntimeException())
            parts.forEach{
                val subParts = it.split("/")
                if (subParts.size == 0) throw ObjParseException("FaceVertex must have a face index.", RuntimeException())
                val vertexIndex = subParts[0].toInt() - currentIndex
                var texCoordIndex = Vertex.MISSING_VALUE
                var normalIndex = Vertex.MISSING_VALUE
                if (subParts.size > 1)  texCoordIndex = tryParseInt(subParts[1])
                if (subParts.size > 2)  normalIndex = tryParseInt(subParts[2])
                if (vertices.size >= vertexIndex) {
                    list.add(Vertex(vertexIndex - 1, texCoordIndex, normalIndex, vertices[vertexIndex - 1]))
                } else {
                    throw ObjParseException("No indexed vertex.", RuntimeException())
                }
            }
            val face =  Face(list, material)

            // parse the face to indices and store positions
            for (i in 0..list.size - 3) {
                indices.add(list[0].vertexIndex)
                indices.add(list[i + 1].vertexIndex)
                indices.add(list[i + 2].vertexIndex)
            }

            return face
        }

        fun parseObj(content : List<String>) : RawModel {
            var totalIndices = 0
            var totalVertex = 0
            val obj = RawModel()
            var currentMaterial = Face.INVALID_MATERIAL

            val currentVertexList = mutableListOf<Vector3f>()
            val currentNormalList = mutableListOf<Vector3f>()
            val currentTexCoordList = mutableListOf<Vector2f>()
            val currentFaces = mutableListOf<Face>()
            val currentIndices = arrayListOf<Int>()

            val boundsMin = Vector3f(0f)
            val boundsMax = Vector3f(0f)

            var currentIndex = 0

            var sectionIndex = -1

            content.forEach {
                // 获取line前面的第一个字
                it.trim()
                val indexOfSpace : Int = it.indexOf(' ')
                val verb = if (indexOfSpace >= 0) it.substring(0, indexOfSpace) else it
                val args = if (indexOfSpace >= 0) it.substring(indexOfSpace).trim() else "";
                when(verb) {
                    "v" -> {
                        val vertex = parseFloatVec3(args)
                        currentVertexList.add(vertex)
                        encapsulateInBounds(vertex, boundsMin, boundsMax)
                        currentIndex++
                    }

                    "vt" -> {
                        currentTexCoordList.add(parseFloatVec2(args))
                    }

                    "vn" -> {
                        currentNormalList.add(parseFloatVec3(args))
                    }

                    "usemtl" -> {
                        sectionIndex++
                        // when there is a new "usemtl" appear, we should add other components parsed before to the obj
                        obj.verticeSection.add(currentVertexList.toMutableList())
                        obj.normalSection.add(currentNormalList.toMutableList())
                        obj.texCoordSection.add(currentTexCoordList.toMutableList())
                        obj.vec3ToArray(currentVertexList, sectionIndex)
                        // clear above
                        currentVertexList.clear()
                        currentNormalList.clear()
                        currentTexCoordList.clear()
                        // clear them
                        if (currentMaterial == "") {
                            currentMaterial = args
                            // currently, facesection has no data, no need to add
                        }
                        else {
                            currentMaterial = args
                            //add the last facesection to obj
                            obj.faceSection.add(currentFaces.toMutableList())
                            obj.indicesSection.add(currentIndices.toIntArray())
                            currentFaces.clear()
                            currentIndices.clear()
                        }
                    }

                    "f" -> {
                        val face = parseFace(args, currentIndices, obj.verticeSection[sectionIndex], currentMaterial, currentIndex - obj.verticeSection[sectionIndex].size, obj)
                        currentFaces.add(face)
                        totalIndices += 3 * (face.vertexs.size - 2)
                        totalVertex += face.vertexs.size
                    }

                    else -> {
                        Log.e(TAG, it)
                    }
                }
            }
            obj.faceSection.add(currentFaces)
            obj.indicesSection.add(currentIndices.toIntArray())
            obj.totalIndices = totalIndices
            obj.totalVertex = totalVertex
            obj.boundsMin = boundsMin
            obj.boundsMax = boundsMax
            return obj
        }
    }
}