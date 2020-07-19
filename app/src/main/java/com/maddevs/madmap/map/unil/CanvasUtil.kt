package com.maddevs.madmap.map.unil

import android.graphics.Canvas
import android.graphics.Paint
import com.maddevs.madmap.map.model.Point
import com.maddevs.madmap.map.model.`object`.BorderObject

object CanvasUtil {
    fun Canvas.drawLine(onePoint: Point, twoPoint: Point, paint: Paint) {
        this.drawLine(
            onePoint.x.toFloat(),
            onePoint.y.toFloat(),
            twoPoint.x.toFloat(),
            twoPoint.y.toFloat(),
            paint
        )
    }
}