package com.maddevs.madmap.map.model.camera

import com.maddevs.madmap.map.model.GeoPoint

class CameraPosition(latitude: Double, longitude: Double, val width: Int, val height: Int) : GeoPoint(latitude, longitude) {

    var centerX: Int = width / 2
    var centerY: Int = height / 2

    fun updatePosition(latitude: Double, longitude: Double) {
        updateCoordinate(latitude, longitude, true)
    }
}