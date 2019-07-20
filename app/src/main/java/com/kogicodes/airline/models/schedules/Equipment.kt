package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Equipment : Serializable {

    @SerializedName("AircraftCode")
    @Expose
    var aircraftCode: String? = null

}