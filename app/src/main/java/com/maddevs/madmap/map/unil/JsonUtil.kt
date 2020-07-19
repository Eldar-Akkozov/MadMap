package com.maddevs.madmap.map.unil

import org.json.JSONArray
import org.json.JSONObject

object JsonUtil {
    fun JSONArray.iterator(): Iterator<JSONObject> = (0 until length()).asSequence().map { get(it) as JSONObject }.iterator()
}