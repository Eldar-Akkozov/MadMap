package com.maddevs.madmap.map.unil

import com.maddevs.madmap.map.model.Point
import kotlin.math.*

object GeoCoordinateConverter {
    private val R_MAJOR = 6378137.0
    private val R_MINOR = 6356752.3142
    private val RATIO = R_MINOR / R_MAJOR
    private val ECCENT = sqrt(1.0 - (RATIO * RATIO))
    private val COM = 0.5 * ECCENT
    private var ZOOM = 10000

    fun converterLatitudeToX(latitude: Double, zoom: Int = ZOOM): Double {
        return (R_MAJOR * Math.toRadians(latitude)) / zoom
    }

    fun converterLongitudeToY(longitude: Double, zoom: Int = ZOOM): Double {
        val Lat=55.751667
        val Long=37.617778
        val rLat=Lat * PI /180
        val rLong=Long*PI/180
        val a=6378137
        val b=6356752.3142
        val f=(a-b)/a
        val e=sqrt(2*f-f.pow(2))
        var X=a * rLong
        var Y=a * Math.log(tan(PI/4+rLat/2)*((1-e*sin(rLat))/(1+e*sin(rLat))).pow(e/2))


        val twoLongitude = min(89.5, max(longitude, -89.5))

        val phi = Math.toRadians(twoLongitude)
        val sinphi = sin(phi)
        var con = ECCENT * sinphi
        con = ((1.0 - con) / (1.0 + con)).pow(COM)
        val ts = tan(0.5 * (PI * 0.5 - phi)) / con
        return (0 - R_MAJOR * ln(ts)) / zoom
    }

    fun converterGeoData(latitude: Double, longitude: Double, zoom: Int = ZOOM): Point {
        return Point(converterLatitudeToX(latitude, zoom), converterLongitudeToY(longitude, zoom))
    }
}