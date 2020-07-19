package com.maddevs.madmap.map.model

import android.util.Log
import com.maddevs.madmap.map.unil.GeoCoordinateConverter.converterGeoData

open class GeoPoint(var latitude: Double, var longitude: Double) : Point(converterGeoData(latitude, longitude)) {

    private var angleRotateXY = 0.0
    private val angleRotate = 270.0
    var zoom = 10000

    var types = "2"

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

    open fun changeZoom(addedZoom: Int) {
        zoom += addedZoom

        if (zoom < 1) {
            zoom = 1
        }

        updateCoordinate(latitude, longitude, true)
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