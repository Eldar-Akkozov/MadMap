package com.maddevs.madmap.map.model

import java.lang.NullPointerException
import kotlin.math.*

open class Point {

    var x: Double
    var y: Double

    constructor(x: Double, y: Double) {
        this.x = x
        this.y = y
    }

    constructor(point: Point) {
        this.x = point.x
        this.y = point.y
    }

    constructor() {
        this.x = Double.NaN
        this.y = Double.NaN
    }

    fun updateData(point: Point) {
        this.x = point.x
        this.y = point.y
    }

    fun distanceTo(point: Point) : Double {
        return sqrt((x - point.x).pow(2) + (y - point.y).pow(2))
    }

    fun bearingTo(point: Point) : Double {
        val rad2deg = 57.2957795130823209

        var theta = atan2(point.y - y, point.x - x)
        if (theta < 0.0) theta += PI * 2

        return rad2deg * theta
    }

    fun rotate(angle: Double) {
        val rad: Double = angle * PI / 180

        val rx = x
        val ry = y

        x = rx * cos(rad) - ry * sin(rad)
        y = ry * cos(rad) + rx * sin(rad)
    }
}