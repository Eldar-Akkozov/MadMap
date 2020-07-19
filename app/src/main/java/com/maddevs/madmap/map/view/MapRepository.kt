package com.maddevs.madmap.map.view

import android.content.Context
import com.maddevs.madmap.map.contract.MapContract
import com.maddevs.madmap.map.model.`object`.*
import com.maddevs.madmap.map.unil.JsonUtil.iterator
import org.json.JSONArray
import java.io.InputStream

class MapRepository(private val context: Context) : MapContract.Repository {

    override fun getShapes(): List<ShapeObject>? {
        val shapes = JSONArray(getAssetsFileString("shapes.json"))

        val result = ArrayList<ShapeObject>()

        for (item in shapes.iterator()) {
            val shapes = ArrayList<ShapeItemObject>()
            val color = item.getString("color")
            val type = item.getString("type")

            for (shape in item.getJSONArray("shape").iterator()) {
                shapes.add(
                    ShapeItemObject(
                        shape.getDouble("latitude"),
                        shape.getDouble("longitude")
                    )
                )
            }

            result.add(ShapeObject(shapes, type, color))
        }

        return result
    }


    override fun getShapesString(): List<StringObject>? {
        val stringsObject = JSONArray(getAssetsFileString("shapes_title.json"))

        val result = ArrayList<StringObject>()

        for (item in stringsObject.iterator()) {
            result.add(
                StringObject(
                    item.getDouble("latitude"),
                    item.getDouble("longitude"),
                    StringDataObject(
                        item.getString("title"),
                        item.getDouble("text_size").toFloat(),
                        item.getString("color")
                    )
                )
            )
        }

        return result
    }

    override fun getBorders(): List<BorderObject>? {
        val stringsObject = JSONArray(getAssetsFileString("borders.json"))

        val result = ArrayList<BorderObject>()

        for (item in stringsObject.iterator()) {
            result.add(
                BorderObject(
                    item.getDouble("latitude"),
                    item.getDouble("longitude"),
                    item.getString("type")
                )
            )
        }

        return result
    }

    override fun getBordersLine(): List<BorderLineObject>? {
        val stringsObject = JSONArray(getAssetsFileString("borders_line.json"))

        val result = ArrayList<BorderLineObject>()

        for (item in stringsObject.iterator()) {
            result.add(
                BorderLineObject(
                    BorderObject(
                        item.getDouble("latitudeOne"),
                        item.getDouble("longitudeOne")
                    ),
                    BorderObject(
                        item.getDouble("latitudeTwo"),
                        item.getDouble("longitudeTwo")
                    ),
                    item.getString("type")
                )
            )
        }

        return result
    }

    private fun getAssetsFileString(fileName: String): String {
        val inputStream: InputStream = context.assets.open(fileName)
        val buffer = ByteArray(inputStream.available())
        inputStream.read(buffer)
        val fileStrign = String(buffer)
        inputStream.close()

        return fileStrign
    }

    override fun getTotalRenderingLevel(): Int {
        return 0
    }
}