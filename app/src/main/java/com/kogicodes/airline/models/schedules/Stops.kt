package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Stops : Serializable {

    @SerializedName("StopQuantity")
    @Expose
    var stopQuantity: Int? = null

}