package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirportModel {
    @SerializedName("AirportResource")
    @Expose
    var airportResource: SingleAirportResource? = null
}
