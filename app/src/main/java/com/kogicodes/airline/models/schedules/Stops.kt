package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Stops {

    @SerializedName("StopQuantity")
    @Expose
    var stopQuantity: Int? = null

}