package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TotalJourney {

    @SerializedName("Duration")
    @Expose
    var duration: String? = null

}