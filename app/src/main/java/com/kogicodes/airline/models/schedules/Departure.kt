package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Departure : Serializable {

    @SerializedName("AirportCode")
    @Expose
    var airportCode: String? = null
    @SerializedName("ScheduledTimeLocal")
    @Expose
    var scheduledTimeLocal: ScheduledTimeLocal? = null

}
