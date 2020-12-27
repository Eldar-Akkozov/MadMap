package com.maddevs.madmap.map.view

import android.app.Activity
import android.content.Context
import android.util.Log
import com.maddevs.madmap.map.contract.Estimation
import com.maddevs.madmap.map.contract.MapContract
import com.maddevs.madmap.map.model.GeoPoint
import com.maddevs.madmap.map.model.Point
import com.maddevs.madmap.map.model.`object`.BorderLineObject
import com.maddevs.madmap.map.model.`object`.BorderObject
import com.maddevs.madmap.map.model.`object`.ShapeObject
import com.maddevs.madmap.map.model.`object`.StringObject
import com.maddevs.madmap.map.module.camera.CameraPosition
import com.maddevs.madmap.map.module.camera.CameraRotate
import com.maddevs.madmap.map.module.camera.CameraZoom

class MapPresenter(context: Context, repository: MapContract.Repository = MapRepository(context)) : MapContract.Presenter {

    private val shapesRendering: List<ShapeObject>? = repository.getShapes()
    private val bordersRendering: List<BorderObject>? = repository.getBorders()
    private val bordersLineRendering: List<BorderLineObject>? = repository.getBordersLine()
    private val shapesStringRendering: List<StringObject>? = repository.getShapesString()

    private lateinit var cameraPosition: CameraPosition
    private lateinit var cameraRotate: CameraRotate
    lateinit var cameraZoom: CameraZoom

    private var widthC: Int = 0
    private var heightY: Int = 0

    private var mX = 0f
    private var mY = 0f

    private var mDX = 0f
    private var mDY = 0f

    override fun getShapes(): List<ShapeObject>? {
        return shapesRendering
    }

    override fun getShapesString(): List<StringObject>? {
        return shapesStringRendering
    }

    override fun getBorders(): List<BorderObject>? {
        return bordersRendering
    }

    override fun getBordersLine(): List<BorderLineObject>? {
        return bordersLineRendering
    }

    override fun touchStart(x: Float, y: Float) {
        mX = x
        mY = y
    }

    override fun touchMove(x: Float, y: Float) {
        val dx = x - mX
        val dy = y - mY

        if (Point(x.toDouble(), y.toDouble()).distanceTo(Point(mX.toDouble(), mY.toDouble())) < 300) {
            moveCoordiante(dx, dy)
        }

        mX = x
        mY = y
    }

    override fun touchDoubleMove(x: Float, y: Float) {
        if (mDX == 0f || mDY == 0f) {
            mDX = x
            mDY = y
        } else {
            val dx = x - mDX
            val dy = y - mDY

            if (Point(x.toDouble(), y.toDouble()).distanceTo(Point(mDX.toDouble(), mDY.toDouble())) < 300) {
                moveCoordiante(dx, dy)
            }

            mDX = x
            mDY = y
        }
    }

    override fun touchDoubleEnd() {
        mDY = 0f
        mDX = 0f
    }

    override fun initCamera(width: Int, height: Int) {
        this.heightY = height / 2
        this.widthC = width / 2

        cameraPosition = CameraPosition(width, height)
        cameraRotate = CameraRotate(width, height)
        cameraZoom = CameraZoom()

        moveCoordiante(
            ((cameraPosition.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraPosition.y * -1) + cameraPosition.centerY).toFloat()
        )
    }

    override fun changeCameraPosition(latitude: Double, longitude: Double) {
        cameraPosition.updatePosition(latitude, longitude)

        moveCoordiante(
            ((cameraPosition.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraPosition.y * -1) + cameraPosition.centerY).toFloat()
        )
    }

    override fun changeCameraPosition(latitude: Double, longitude: Double, zoomLevel: Int) {
        estimationData(object : Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.updateCoordinateZoom(zoomLevel)
            }
        })

        cameraPosition.updatePosition(latitude, longitude)
        cameraPosition.updateCoordinateZoom(zoomLevel)

        moveCoordiante(
            ((cameraPosition.x * -1) + cameraPosition.centerX).toFloat(),
            ((cameraPosition.y * -1) + cameraPosition.centerY).toFloat()
        )
    }

    override fun changeCameraPosition(zoom: Double, type: CameraZoom.Type) {
        zoomCoordiante(zoom, type)
    }

    override fun changeCameraPosition(rotate: Double) {
        rotateCoordinate(rotate)

        moveCoordiante(
            ((cameraRotate.x * -1) + cameraRotate.regulatoryСenterX).toFloat(),
            ((cameraRotate.y * -1) + cameraRotate.regulatoryСenterY).toFloat(),
            true
        )
    }

    override fun changeCameraPosition(rotate: Double, regulatoryPoint: Point) {
        rotateNotSaveCoordinate(rotate)

        moveCoordiante(
            ((cameraRotate.x * -1) + regulatoryPoint.x).toFloat(),
            ((cameraRotate.y * -1) + regulatoryPoint.y).toFloat(),
            true
        )
    }

    override fun changeCameraPosition(point: Point) {
        moveCoordiante(
            ((point.x * -1) + cameraRotate.regulatoryСenterX).toFloat(),
            ((point.y * -1) + cameraRotate.regulatoryСenterY).toFloat()
        )
    }

    private fun moveCoordiante(dx: Float, dy: Float, rotateMode: Boolean = false) {
        if (rotateMode) {
            cameraRotate.move(dx, dy)
        }

        estimationData(object : Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.move(dx, dy)
            }
        })
    }

    private fun zoomCoordiante(zoom: Double, type: CameraZoom.Type) {
        moveCoordiante((widthC * -1).toFloat(), (heightY * -1).toFloat())

        estimationData(object : Estimation<GeoPoint> {
            override fun counting(item: GeoPoint) {
                item.changeZoom(zoom, type)
            }
        })

        moveCoordiante((widthC).toFloat(), (heightY).toFloat())
    }

    private fun rotateCoordinate(rotate: Double) {
        cameraRotate.changeRotate(rotate)

        estimationData(object : Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.changeRotate(rotate)
            }
        })
    }

    private fun rotateNotSaveCoordinate(rotate: Double) {
        cameraRotate.rotate(rotate)

        estimationData(object : Estimation<GeoPoint>{
            override fun counting(item: GeoPoint) {
                item.rotate(rotate)
            }
        })
    }

    fun estimationData(estimation: Estimation<GeoPoint>) {
        estimation.counting(cameraPosition)

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