package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Schedule {

    @SerializedName("TotalJourney")
    @Expose
    var totalJourney: TotalJourney? = null
    @SerializedName("Flight")
    @Expose
    var flight: List<Flight>? = null
}
