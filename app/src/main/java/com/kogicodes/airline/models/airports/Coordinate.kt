package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Coordinate : Serializable {


    @SerializedName("Latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("Longitude")
    @Expose
    var longitude: Double? = null

    constructor(latitude: Double?, longitude: Double?) {
        this.latitude = latitude
        this.longitude = longitude
    }
}
