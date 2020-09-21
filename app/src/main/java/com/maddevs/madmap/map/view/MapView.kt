package com.maddevs.madmap.map.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorInt
import com.maddevs.madmap.map.contract.MapContract
import com.maddevs.madmap.map.module.camera.CameraZoom
import com.maddevs.madmap.map.module.touch.TouchManager
import com.maddevs.madmap.map.unil.PathUtil.lineTo
import com.maddevs.madmap.map.unil.PathUtil.moveTo
import com.maddevs.madmap.map.unil.CanvasUtil.drawLine
import com.maddevs.madmap.map.unil.CanvasUtil.drawText

class MapView : View, MapContract.View {

    @ColorInt
    private val backgroundColor: Int = Color.parseColor("#aadaff")

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private val paintLimiter = Paint()
    private val paintShape = Paint()
    private val textPaint = Paint()

    private lateinit var touchManager: TouchManager
    private lateinit var presenter: MapPresenter
    private lateinit var bitmap: Bitmap

    private var drawCanvas: Canvas? = null

    private var activity: Activity? = null

    override fun initMap(activity: Activity) {
        this.activity = activity
        presenter = MapPresenter(activity)

        paintLimiter.strokeWidth = 1.5f
        paintLimiter.color = Color.parseColor("#95C0E0")
        paintLimiter.style = Paint.Style.STROKE

        post {
            touchManager = TouchManager(presenter, width, height)
            presenter.initCamera(width, height)
            initMapCanvas()
        }
    }

    private fun initMapCanvas() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(bitmap)

        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (drawCanvas != null) {
            canvas.save()

            drawCanvas!!.drawColor(backgroundColor)

            presenter.getShapes()?.forEach {
                val shape = Path()

                for ((index, shapeItem) in it.shapeList.withIndex()) {
                    if (index == 0) {
                        shape.moveTo(shapeItem)
                    } else {
                        shape.lineTo(shapeItem)
                    }
                }

                drawCanvas!!.save()

                paintShape.color = Color.parseColor(it.color)
                drawCanvas!!.drawPath(shape, paintShape)

                drawCanvas!!.restore()
            }

            presenter.getShapesString()?.forEach {
                drawCanvas!!.save()

                textPaint.textSize = it.stringData.size
                textPaint.textAlign = Paint.Align.CENTER

                drawCanvas!!.drawText(it.stringData.string, it, textPaint)

                drawCanvas!!.restore()
            }

            presenter.getBordersLine()?.forEach {
                drawCanvas!!.save()

                drawCanvas!!.drawLine(it.pointOne, it.pointTwo, paintLimiter)

                drawCanvas!!.restore()
            }

            canvas.drawBitmap(bitmap, 0f, 0f, bitmapPaint)
            canvas.restore()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        touchManager.touch(event)
        invalidate()
        return true
    }

    override fun onChangeCameraPosition(zoom: Double, type: CameraZoom.Type) {
        post {
            presenter.changeCameraPosition(zoom, type)
            invalidate()
        }
    }

    override fun onChangeCameraPosition(latitude: Double, longitude: Double) {
        post {
            presenter.changeCameraPosition(latitude, longitude)
            invalidate()
        }
    }

    override fun onChangeCameraPosition(rotate: Double) {
        post {
            presenter.changeCameraPosition(rotate)
            invalidate()
        }
    }
}