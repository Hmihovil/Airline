package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class OperatingCarrier {

    @SerializedName("AirlineID")
    @Expose
    var airlineID: String? = null

}
