package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MarketingCarrier {

    @SerializedName("AirlineID")
    @Expose
    var airlineID: String? = null
    @SerializedName("FlightNumber")
    @Expose
    var flightNumber: Int? = null

}
