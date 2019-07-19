package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirportsModel {
    @SerializedName("AirportResource")
    @Expose
    var airportResource: AirportResource? = null
}
