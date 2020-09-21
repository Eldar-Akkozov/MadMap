package com.maddevs.madmap.map.module.camera

import com.maddevs.madmap.map.model.Point

class CameraRotate(width: Int, height: Int) : Point((width / 2).toDouble(), (height / 2).toDouble()) {

    var regulatoryСenterX: Int = width / 2
    var regulatoryСenterY: Int = height / 2

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