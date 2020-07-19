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
import com.maddevs.madmap.map.unil.PathUtil.lineTo
import com.maddevs.madmap.map.unil.PathUtil.moveTo
import com.maddevs.madmap.map.unil.CanvasUtil.drawLine

class MapView : View, MapContract.View {

    @ColorInt
    private val backgroundColor: Int = Color.parseColor("#aadaff")

    constructor(context: Context) : super(context, null)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private val bitmapPaint = Paint(Paint.DITHER_FLAG)

    private lateinit var bitmap: Bitmap
    private lateinit var drawCanvas: Canvas
    private lateinit var presenter: MapPresenter

    fun initMap(activity: Activity) {
        presenter = MapPresenter(activity)

        post {
            presenter.initCamera(width, height)
            initMapCanvas()
        }
    }

    private fun initMapCanvas() {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        drawCanvas = Canvas(bitmap)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()

        drawCanvas.drawColor(backgroundColor)

        val shapeObjectRendering = presenter.shapesRendering
        val shapeStringObjectRendering = presenter.shapesStringRendering
        val borderLineObjectsRendering = presenter.bordersLineRendering

        if (shapeObjectRendering != null) {
            for (item in shapeObjectRendering) {
                val shape = Path()

                for ((index, shapeItem) in item.shapeList.withIndex()) {
                    if (index == 1) {
                        shape.moveTo(shapeItem)
                    } else {
                        shape.lineTo(shapeItem)
                    }
                }

                drawCanvas.save()

                val paintShape = Paint()
                paintShape.color = Color.parseColor(item.color)
                drawCanvas.drawPath(shape, paintShape)

                drawCanvas.restore()
            }
        }

        if (shapeStringObjectRendering != null) {
            for (item in shapeStringObjectRendering) {
                drawCanvas.save()

                val textPaint = Paint()
                textPaint.textSize = item.stringData.size
                textPaint.textAlign = Paint.Align.CENTER

                drawCanvas.drawText(
                    item.stringData.string,
                    item.x.toFloat(),
                    item.y.toFloat(),
                    textPaint
                )

                drawCanvas.restore()
            }
        }

        if (borderLineObjectsRendering != null) {
            for (item in borderLineObjectsRendering) {
                drawCanvas.save()

                val paint = Paint()
                paint.strokeWidth = 1.5f
                paint.color = Color.parseColor("#a0a0a0")
                paint.style = Paint.Style.STROKE

                drawCanvas.drawLine(item.pointOne, item.pointTwo, paint)

                drawCanvas.restore()
            }
        }

        presenter.cameraUpdater

        val s = Paint()
        s.style = Paint.Style.FILL
        s.color = Color.BLACK

        drawCanvas.drawCircle(
            presenter.cameraUpdater.x.toFloat(),
            presenter.cameraUpdater.y.toFloat(),
            30F,
            s
        )

        canvas.drawBitmap(bitmap, 0f, 0f, bitmapPaint)

        canvas.restore()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                presenter.onTouchStart(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                presenter.onTouchMove(x, y)
                invalidate()
            }
        }
        return true
    }

    override fun changeCameraPosition(zoom: Int) {
        post {
            presenter.onChangeCameraPosition(zoom)
            invalidate()
        }
    }

    override fun changeCameraPosition(latitude: Double, longitude: Double) {
        post {
            presenter.onChangeCameraPosition(latitude, longitude)
            invalidate()
        }
    }

    override fun changeCameraPosition(rotate: Double) {
        post {
            presenter.onChangeCameraPosition(rotate)
            invalidate()
        }
    }
}