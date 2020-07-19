package com.maddevs.madmap.map.model.`object`

import com.maddevs.madmap.map.model.GeoPoint

class BorderObject(latitude: Double, longitude: Double, val type: String = "NULL") : GeoPoint(latitude, longitude)