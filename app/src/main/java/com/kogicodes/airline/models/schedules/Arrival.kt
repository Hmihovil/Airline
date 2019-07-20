package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Arrival : Serializable {

    @SerializedName("AirportCode")
    @Expose
    var airportCode: String? = null
    @SerializedName("ScheduledTimeLocal")
    @Expose
    var scheduledTimeLocal: ScheduledTimeLocal_? = null
    @SerializedName("Terminal")
    @Expose
    var terminal: Terminal? = null
        get() {
            if (field == null) {
                this.terminal = Terminal()
            }
            return field
        }

}
