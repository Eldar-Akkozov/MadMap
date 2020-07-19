package com.maddevs.madmap.map.view

import android.app.Activity
import com.maddevs.madmap.map.contract.MapContract
import com.maddevs.madmap.map.model.GeoPoint
import com.maddevs.madmap.map.model.`object`.BorderLineObject
import com.maddevs.madmap.map.model.`object`.BorderObject
import com.maddevs.madmap.map.model.`object`.ShapeObject
import com.maddevs.madmap.map.model.`object`.StringObject
import com.maddevs.madmap.map.model.camera.CameraPosition
import com.maddevs.madmap.map.model.camera.CameraUpdater
import com.maddevs.madmap.map.model.camera.CameraZoom

class MapPresenter(activity: Activity) : MapContract.Presenter {

    private val repository: MapRepository = MapRepository(activity)

    private var mX = 0f
    private var mY = 0f

    val shapesRendering: List<ShapeObject>?
    val bordersRendering: List<BorderObject>?
    val bordersLineRendering: List<BorderLineObject>?
    val shapesStringRendering: List<StringObject>?

    private lateinit var cameraPosition: CameraPosition
    lateinit var cameraUpdater: CameraUpdater
    private lateinit var cameraZoom: CameraZoom

    init {
        shapesRendering = repository.getShapes()
        bordersRendering = repository.getBorders()
        bordersLineRendering = repository.getBordersLine()
        shapesStringRendering = repository.getShapesString()
    }

    override fun onTouchStart(x: Float, y: Float) {
        mX = x
        mY = y
    }

    override fun onTouchMove(x: Float, y: Float) {
        val dx = x - mX
        val dy = y - mY

        moveCoordiante(dx, dy)

        mX = x
        mY = y
    }

    fun initCamera(width: Int, height: Int) {
        cameraPosition = CameraPosition(0.0, 0.0, width, height)
        cameraUpdater = CameraUpdater(cameraPosition.centerX.toDouble(), cameraPosition.centerY.toDouble())
        cameraZoom = CameraZoom()

        moveCoordiante(
            ((cameraPosition.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraPosition.y * -1) + cameraPosition.centerY).toFloat()
        )
    }

    override fun onChangeCameraPosition(latitude: Double, longitude: Double) {
        cameraPosition.updatePosition(latitude, longitude)

        moveCoordiante(
            ((cameraPosition.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraPosition.y * -1) + cameraPosition.centerY).toFloat()
        )
    }

    override fun onChangeCameraPosition(zoom: Int) {
        zoomCoordiante(zoom)
    }

    override fun onChangeCameraPosition(rotate: Double) {
        rotateCoordinate(rotate)

        moveCoordiante(
            ((cameraUpdater.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraUpdater.y * -1) + cameraPosition.centerY).toFloat(),
            true
        )
    }

    private fun moveCoordiante(dx: Float, dy: Float, rotateMode: Boolean = false) {
        if (rotateMode) {
            cameraUpdater.move(dx, dy)
        }

        estimationData(object : MapContract.Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.move(dx, dy)
            }
        })
    }

    private fun zoomCoordiante(zoom: Int) {
        estimationData(object : MapContract.Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.changeZoom(zoom)
            }
        })

        moveCoordiante(((cameraZoom.point1.x - cameraZoom.point2.x).toFloat()), ((cameraZoom.point1.y - cameraZoom.point2.y).toFloat()))
    }

    private fun rotateCoordinate(rotate: Double) {
        cameraUpdater.changeRotate(rotate)

        estimationData(object : MapContract.Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.changeRotate(rotate)
            }
        })
    }

    fun estimationData(estimation: MapContract.Estimation<GeoPoint>) {
        estimation.counting(cameraPosition)
        estimation.counting(cameraZoom)

        bordersLineRendering?.forEach {
            estimation.counting(it.pointOne)
            estimation.counting(it.pointTwo)
        }

        bordersRendering?.forEach {
            estimation.counting(it)
        }

        shapesRendering?.forEach {
            it.shapeList.forEach { item ->
                estimation.counting(item)
            }
        }

        shapesStringRendering?.forEach {
            estimation.counting(it)
        }
    }
}