package com.maddevs.madmap.map.contract

import android.app.Activity
import com.maddevs.madmap.map.model.Point
import com.maddevs.madmap.map.model.`object`.BorderLineObject
import com.maddevs.madmap.map.model.`object`.BorderObject
import com.maddevs.madmap.map.model.`object`.ShapeObject
import com.maddevs.madmap.map.model.`object`.StringObject
import com.maddevs.madmap.map.module.camera.CameraZoom

interface MapContract {
    interface View {
        fun onChangeCameraPosition(zoom: Double, type: CameraZoom.Type)
        fun onChangeCameraPosition(latitude: Double, longitude: Double)
        fun onChangeCameraPosition(latitude: Double, longitude: Double, zoomLevel: Int)
        fun onChangeCameraPosition(rotate: Double)
    }

    interface Presenter {
        fun initCamera(width: Int, height: Int)

        fun touchStart(x: Float, y: Float)
        fun touchMove(x: Float, y: Float)

        fun touchDoubleMove(x: Float, y: Float)
        fun touchDoubleEnd()

        fun changeCameraPosition(zoom: Double, type: CameraZoom.Type)
        fun changeCameraPosition(latitude: Double, longitude: Double)
        fun changeCameraPosition(latitude: Double, longitude: Double, zoomLevel: Int)
        fun changeCameraPosition(rotate: Double)
        fun changeCameraPosition(rotate: Double, regulatoryPoint: Point)
        fun changeCameraPosition(point: Point)

        fun getShapes(): List<ShapeObject>?
        fun getShapesString(): List<StringObject>?
        fun getBorders(): List<BorderObject>?
        fun getBordersLine(): List<BorderLineObject>?
    }

    interface Repository {
        fun getShapes(): List<ShapeObject>?
        fun getShapesString(): List<StringObject>?
        fun getBorders(): List<BorderObject>?
        fun getBordersLine(): List<BorderLineObject>?
    }
}