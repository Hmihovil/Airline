package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Position {

    @SerializedName("Coordinate")
    @Expose
    var coordinate: Coordinate? = null

}
