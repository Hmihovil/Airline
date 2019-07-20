package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TotalJourney : Serializable {

    @SerializedName("Duration")
    @Expose
    var duration: String? = null

}