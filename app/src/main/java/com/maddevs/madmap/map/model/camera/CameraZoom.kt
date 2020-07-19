package com.maddevs.madmap.map.model.camera

import android.util.Log
import com.maddevs.madmap.map.model.GeoPoint
import com.maddevs.madmap.map.model.Point

class CameraZoom(latitude: Double = 18.902389796969448, longitude: Double = 14.877051673829557) : GeoPoint(latitude, longitude) {

    init {
        types = "3"
    }

    var point1 = Point()
    var point2 = Point()

    override fun changeZoom(addedZoom: Int) {
        point1 = Point(x, y)
        Log.d("testZoom O", "${point1.x} ${point1.y}")

        zoom += addedZoom

        if (zoom < 1) {
            zoom = 1
        }

        updateCoordinate(latitude, longitude, true)


        point2 = Point(x, y)
        Log.d("testZoom T", "${point2.x} ${point2.y}")

        Log.d("testZoom D", "${point1.distanceTo(point2)}")
        Log.d("testZoom Y", "${point1.x - point2.x} ${point1.y - point2.y}")
    }
}