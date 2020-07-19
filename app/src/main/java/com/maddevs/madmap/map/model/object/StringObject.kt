package com.maddevs.madmap.map.model.`object`

import com.maddevs.madmap.map.model.GeoPoint

class StringObject(latitude: Double, longitude: Double, val stringData: StringDataObject) : GeoPoint(latitude, longitude)