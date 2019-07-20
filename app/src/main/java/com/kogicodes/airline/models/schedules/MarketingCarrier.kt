package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class MarketingCarrier : Serializable {

    @SerializedName("AirlineID")
    @Expose
    var airlineID: String? = null
    @SerializedName("FlightNumber")
    @Expose
    var flightNumber: Int? = null

}
