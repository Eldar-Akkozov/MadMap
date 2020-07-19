package com.maddevs.madmap.map.unil

import android.graphics.Path
import com.maddevs.madmap.map.model.Point

object PathUtil {
    fun Path.moveTo(point: Point) {
        this.moveTo(point.x.toFloat(), point.y.toFloat())
    }

    fun Path.lineTo(point: Point) {
        this.lineTo(point.x.toFloat(), point.y.toFloat())
    }
}