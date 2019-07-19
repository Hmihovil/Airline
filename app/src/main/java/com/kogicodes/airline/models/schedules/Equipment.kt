package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Equipment {

    @SerializedName("AircraftCode")
    @Expose
    var aircraftCode: String? = null

}