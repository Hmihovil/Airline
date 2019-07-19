package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Coordinate {


    @SerializedName("Latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("Longitude")
    @Expose
    var longitude: Double? = null
}
