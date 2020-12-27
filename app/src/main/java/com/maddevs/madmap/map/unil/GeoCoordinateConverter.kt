package com.maddevs.madmap.map.unil

import com.maddevs.madmap.map.model.Point
import kotlin.math.*

object GeoCoordinateConverter {
    private const val A = 6378137
    private const val B = 6356752.3142
    private var ZOOM = 10000

    fun converterLatitudeToX(latitude: Double, zoom: Int = ZOOM): Double {
        val rLat = latitude * PI / 180
        val f = (A - B) / A
        val e = sqrt(2 * f - f.pow(2))

        return (A * Math.log(tan(PI / 4 + rLat / 2) * ((1 - e * sin(rLat)) / (1 + e * sin(rLat))).pow(e / 2))) / zoom
    }

    fun converterLongitudeToY(longitude: Double, zoom: Int = ZOOM): Double {
        val rLong= longitude * PI / 180
        return (A * rLong) / zoom
    }

    fun converterGeoData(latitude: Double, longitude: Double, zoom: Int = ZOOM): Point {
        return Point(converterLatitudeToX(latitude, zoom), converterLongitudeToY(longitude, zoom))
    }
}