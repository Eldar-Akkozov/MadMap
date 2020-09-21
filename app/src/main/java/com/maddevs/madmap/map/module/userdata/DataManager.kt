package com.maddevs.madmap.map.module.userdata

import com.maddevs.madmap.map.model.usermodel.Line
import com.maddevs.madmap.map.model.usermodel.Marker
import com.maddevs.madmap.map.model.usermodel.Shape
import com.maddevs.madmap.map.model.usermodel.Сircle

class DataManager {

    private var lineList: ArrayList<Line> = ArrayList()
    private var markerList: ArrayList<Marker> = ArrayList()
    private var shapeList: ArrayList<Shape> = ArrayList()
    private var circleList: ArrayList<Сircle> = ArrayList()

    fun addLine(line: Line) {
        lineList.add(line)
    }

    fun addMarker(marker: Marker) {
        markerList.add(marker)
    }

    fun addShape(shape: Shape) {
        shapeList.add(shape)
    }

    fun addСircle(circle: Сircle) {
        circleList.add(circle)
    }
}