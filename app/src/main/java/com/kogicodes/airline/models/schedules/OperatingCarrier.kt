package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OperatingCarrier : Serializable {

    @SerializedName("AirlineID")
    @Expose
    var airlineID: String? = null

}
