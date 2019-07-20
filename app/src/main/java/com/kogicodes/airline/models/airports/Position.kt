package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Position : Serializable {

    @SerializedName("Coordinate")
    @Expose
    var coordinate: Coordinate? = null


    constructor(coordinate: Coordinate?) {
        this.coordinate = coordinate
    }
}
