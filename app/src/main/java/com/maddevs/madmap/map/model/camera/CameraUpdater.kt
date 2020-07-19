package com.maddevs.madmap.map.model.camera

import android.util.Log
import com.maddevs.madmap.map.model.GeoPoint
import com.maddevs.madmap.map.model.Point

class CameraUpdater(x: Double, y: Double) : Point(x, y) {

    private var angleRotateXY = 0.0

    fun changeRotate(rotate: Double) {
        rotate(rotate - angleRotateXY)

        angleRotateXY = rotate
    }

    fun move(dx: Float, dy: Float) {
        x += dx
        y += dy
    }
}