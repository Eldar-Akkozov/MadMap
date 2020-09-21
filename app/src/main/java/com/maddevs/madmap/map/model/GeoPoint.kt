package com.maddevs.madmap.map.model

import com.maddevs.madmap.map.module.camera.CameraZoom
import com.maddevs.madmap.map.unil.GeoCoordinateConverter.converterGeoData

open class GeoPoint(var latitude: Double, var longitude: Double) : Point(converterGeoData(latitude, longitude)) {

    private var angleRotateXY = 0.0
    private val angleRotate = 270.0
    var zoom = 10000

    var dx: Float = 0f
    var dy: Float = 0f

    init {
        rotate(angleRotate)
    }

    open fun move(dx: Float, dy: Float) {
        this.dx += dx
        this.dy += dy

        x += dx
        y += dy
    }

    open fun changeZoom(addedZoom: Double, type: CameraZoom.Type) {
        when (type) {
            CameraZoom.Type.MINUS -> {
                x /= addedZoom
                y /= addedZoom
            }
            CameraZoom.Type.PLUS -> {
                x *= addedZoom
                y *= addedZoom
            }
        }
    }

    open fun changeRotate(rotate: Double) {
        rotate(rotate - angleRotateXY)

        angleRotateXY = rotate
    }

    protected fun updateCoordinate(latitude: Double, longitude: Double, changeZoom: Boolean) {
        updateData(converterGeoData(latitude, longitude, zoom))
        rotate(angleRotate + angleRotateXY)

        if (changeZoom) {
            x += dx
            y += dy
        }
    }
}