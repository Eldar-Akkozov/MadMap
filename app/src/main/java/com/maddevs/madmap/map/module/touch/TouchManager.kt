package com.maddevs.madmap.map.module.touch

import android.view.MotionEvent
import com.maddevs.madmap.map.contract.MapContract
import com.maddevs.madmap.map.model.Point
import com.maddevs.madmap.map.module.camera.CameraZoom
import kotlin.math.abs

class TouchManager(var presenter: MapContract.Presenter, width: Int, height: Int) {

    private val centerPoint = Point((width / 2).toDouble(), (height / 2).toDouble())

    private var checkBearing = 0.0
    private var checkTepm = 0
    private var checkStartBearing = 0.0
    private var checkEndBearing = 0.0

    private var startBearing = 0.0
    private var distancePoint = 0.0

    fun touch(event: MotionEvent) {
        if (event.pointerCount > 1) {
            val firstPoint = Point(event.getX(0).toDouble(), event.getY(0).toDouble())
            val secondPoint = Point(event.getX(1).toDouble(), event.getY(1).toDouble())

            val middlePoint = firstPoint.middleTo(secondPoint)

            val distance = centerPoint.distanceTo(middlePoint)

            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (distancePoint == 0.0) {
                        distancePoint = firstPoint.distanceTo(secondPoint)
                    }

                    presenter.touchDoubleMove(middlePoint.x.toFloat(), middlePoint.y.toFloat())

                    val checkDistance = distancePoint - firstPoint.distanceTo(secondPoint)

                    if (checkDistance > 10) {
                        distancePoint = 0.0

                        presenter.changeCameraPosition(1.02, CameraZoom.Type.PLUS)
                        presenter.changeCameraPosition(centerPoint.pointTo(centerPoint.bearingTo(middlePoint), (distance / 100) * 5))
                    } else if (checkDistance < -10) {
                        distancePoint = 0.0

                        presenter.changeCameraPosition(1.02, CameraZoom.Type.MINUS)
                        presenter.changeCameraPosition(centerPoint.pointTo(middlePoint.bearingTo(centerPoint), (distance / 100) * 5))
                    }

                    if (checkStartBearing != 0.0) {
                        checkBearing = middlePoint.bearingTo(firstPoint) - checkStartBearing

                        if (checkEndBearing == 0.0) {
                            checkEndBearing = checkBearing
                        }

                        if (abs(checkBearing) > abs(checkEndBearing)) {
                            checkEndBearing = checkBearing
                            checkTepm++
                        }

                        if (checkTepm > 6) {
                            if (startBearing != 0.0) {
                                presenter.changeCameraPosition((middlePoint.bearingTo(firstPoint) - startBearing), middlePoint)
                            }

                            startBearing = middlePoint.bearingTo(firstPoint)
                        }
                    }

                    checkStartBearing = middlePoint.bearingTo(firstPoint)
                }
                MotionEvent.ACTION_POINTER_UP -> {
                    distancePoint = 0.0
                    startBearing = 0.0
                    checkBearing = 0.0
                    checkEndBearing = 0.0
                    checkStartBearing = 0.0
                    checkTepm = 0
                    presenter.touchDoubleEnd()
                }
            }
        } else {
            val x = event.x
            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    presenter.touchStart(x, y)
                }
                MotionEvent.ACTION_MOVE -> {
                    presenter.touchMove(x, y)
                }
            }
        }
    }
}