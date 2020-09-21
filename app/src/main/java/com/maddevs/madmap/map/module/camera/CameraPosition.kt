package com.maddevs.madmap.map.module.camera

import com.maddevs.madmap.map.model.GeoPoint

class CameraPosition(width: Int, height: Int) : GeoPoint(0.0, 0.0) {

    var centerX: Int = width / 2
    var centerY: Int = height / 2

    fun updatePosition(latitude: Double, longitude: Double) {
        updateCoordinate(latitude, longitude, true)
    }
}