package com.maddevs.madmap.map.contract

import com.maddevs.madmap.map.model.`object`.BorderLineObject
import com.maddevs.madmap.map.model.`object`.BorderObject
import com.maddevs.madmap.map.model.`object`.ShapeObject
import com.maddevs.madmap.map.model.`object`.StringObject

interface MapContract {
    interface View {
        fun changeCameraPosition(zoom: Int)
        fun changeCameraPosition(latitude: Double, longitude: Double)
        fun changeCameraPosition(rotate: Double)
    }

    interface Presenter {
        fun onTouchStart(x: Float, y: Float)
        fun onTouchMove(x: Float, y: Float)
        fun onChangeCameraPosition(zoom: Int)
        fun onChangeCameraPosition(latitude: Double, longitude: Double)
        fun onChangeCameraPosition(rotate: Double)
    }

    interface Repository {
        fun getShapes(): List<ShapeObject>?
        fun getShapesString(): List<StringObject>?
        fun getBorders(): List<BorderObject>?
        fun getBordersLine(): List<BorderLineObject>?
        fun getTotalRenderingLevel(): Int
    }

    interface Estimation<T> {
        fun counting(item: T)
    }
}