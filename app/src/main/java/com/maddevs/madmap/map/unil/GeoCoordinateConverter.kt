package com.maddevs.madmap.map.unil

import com.maddevs.madmap.map.model.Point
import kotlin.math.*

object GeoCoordinateConverter {
    private val D_R = PI / 180.0
    private val R_D = 180.0 / PI
    private val R_MAJOR = 6378137.0
    private val R_MINOR = 6356752.3142
    private val RATIO = R_MINOR / R_MAJOR
    private val ECCENT = sqrt(1.0 - (RATIO * RATIO))
    private val COM = 0.5 * ECCENT
    private var ZOOM = 10000

    fun converterLatitudeToX(latitude: Double, zoom: Int = ZOOM): Double {
        return (R_MAJOR * deg_rad(latitude)) / zoom
    }

    private fun deg_rad(ang: Double): Double {
        return ang * D_R
    }

    private fun rad_deg(ang: Double): Double {
        return ang * R_D
    }

    fun converterLongitudeToY(longitude: Double, zoom: Int = ZOOM): Double {
        val twoLongitude = min(89.5, max(longitude, -89.5))

        val phi = deg_rad(twoLongitude)
        val sinphi = sin(phi)
        var con = ECCENT * sinphi
        con = ((1.0 - con) / (1.0 + con)).pow(COM)
        val ts = tan(0.5 * (PI * 0.5 - phi)) / con
        return (0 - R_MAJOR * ln(ts)) / zoom
    }

    fun converterXtoLongitude(x: Double): Double {
        return rad_deg(x) / R_MAJOR
    }

    fun converterYtoLatitude(y: Double): Double {
        val ts = exp(-y / R_MAJOR)
        var phi: Double = (PI / 2) - 2 * atan(ts)
        var dphi = 1.0
        var i = 0
        while (abs(dphi) > 0.000000001 && i < 15) {
            val con = ECCENT * sin(phi)
            dphi = (PI / 2) - 2 * atan(ts * ((1.0 - con) / (1.0 + con)).pow(COM)) - phi
            phi += dphi
            i++
        }
        return rad_deg(phi)
    }

    fun converterGeoData(latitude: Double, longitude: Double, zoom: Int = ZOOM): Point {
        return Point(converterLatitudeToX(latitude, zoom), converterLongitudeToY(longitude, zoom))
    }
}