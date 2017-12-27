package com.a.a.objlib.entity

import android.opengl.GLES30
import com.a.a.objlib.gl.`object`.Program
import com.a.a.objlib.gl.`object`.VAO
import com.a.a.objlib.gl.attribute.UniformMatrix
import com.a.a.objlib.math.Matrix4f
import com.a.a.objlib.math.Vector3f
import com.a.a.objlib.model.Material
import com.a.a.objlib.model.RawModel


/**
 * Created by cuicui on 20/12/2017.
 */
class ObjModel {

    companion object {
        val FLOATS_PER_VERTEX = 3
        val FLOATS_PER_COORD = 2
        val FLOATS_PER_COLOR = 4
    }

    var vaos  = mutableListOf<VAO>()
    private var program: Program? = null
        private set
    private val vertexShader : String
    private val fragmentShader : String

    private val locationAttributes : Array<String>
    private var LOCATION_ATTRIBUTE_ARRAY = intArrayOf(0, 1)

    private var uniformAttribute = arrayOf(
            "modelMatrix",
            "viewMatrix",
            "projectionMatrix"
    )
    private var modelMatrix : Matrix4f
    private var modelMU : UniformMatrix
    private var viewMU : UniformMatrix
    private var projectMU : UniformMatrix

    private var isCreated = false
    private val obj : RawModel
    private val materials : Map<String, Material>


    constructor(obj: RawModel, vertexString: String, fragmentString: String, materials : Map<String, Material>) {
        this.obj = obj
        this.materials = materials
        this.vertexShader = vertexString
        this.fragmentShader = fragmentString
        locationAttributes = arrayOf(
                "aPosition",
                "aColor"
        )
        this.modelMatrix = Matrix4f()
        this.modelMatrix.setIdentity()

        modelMU = UniformMatrix("modelMatrix")
        viewMU = UniformMatrix("viewMatrix")
        projectMU = UniformMatrix("projectionMatrix")
    }

    constructor(obj: RawModel, vertexString: String, fragmentString: String, locationAttributes : Array<String>, materials : Map<String, Material>) {
        this.obj = obj
        this.materials = materials
        this.vertexShader = vertexString
        this.fragmentShader = fragmentString
        this.locationAttributes = locationAttributes
        this.modelMatrix = Matrix4f()
        this.modelMatrix.setIdentity()

        modelMU = UniformMatrix("modelMatrix")
        viewMU = UniformMatrix("viewMatrix")
        projectMU = UniformMatrix("projectionMatrix")
    }

    private var vao : VAO? = null
    fun createShader() {
        program = Program(vertexShader, fragmentShader, locationAttributes)

        modelMU.storeUniformLocation(program!!.programID)
        viewMU.storeUniformLocation(program!!.programID)
        projectMU.storeUniformLocation(program!!.programID)

        obj.indicesSection.forEachIndexed { index, it ->
            val vao = VAO.create()
            vao.bind()
            vao.createIndexBuffer(it)
            vao.createFloatAttribute(0, obj.verticeFloatSection[index], FLOATS_PER_VERTEX)
            vao.createFloatAttribute(1, materials.get(obj.faceSection[index][0].matrialName)!!.getColor(obj.verticeSection[index].size), FLOATS_PER_COLOR)
            vao.unbind()
            this.vaos.add(vao)
        }
    }

    private val ASSET_DISPLAY_SIZE = 5f
    private var lastFrameTime: Long = 0
    private val MODEL_ROTATION_SPEED_DPS = 45.0f
    private var angleDegrees: Float = 0f

    fun render(modelM : Matrix4f, viewM : Matrix4f, projectM : Matrix4f) {
        program!!.useProgram()
        val boundsSize = obj.getBoundSize()
        val maxDimension = Math.max(boundsSize.x, Math.max(boundsSize.y, boundsSize.z))
        val scale = ASSET_DISPLAY_SIZE / maxDimension

        val now = System.currentTimeMillis()
        val deltaT = Math.min((now - lastFrameTime) * 0.001f, 0.1f)
        lastFrameTime = now
        angleDegrees += deltaT * MODEL_ROTATION_SPEED_DPS

        modelM.translate(obj.getBoundsCenter().reverse()).scale(Vector3f(scale)).rotate(angleDegrees, Vector3f(0f, 1f, 0f))

        modelMU.loadMatrix(modelM)
        viewMU.loadMatrix(viewM)
        projectMU.loadMatrix(projectM)
        vaos.forEach {
            it.bind(LOCATION_ATTRIBUTE_ARRAY)
            GLES30.glDrawElements(GLES30.GL_TRIANGLES, it.indexCount, GLES30.GL_UNSIGNED_INT, 0)
            it.unbind()
        }
        program!!.unuseProgram()
    }
}