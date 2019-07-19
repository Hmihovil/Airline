package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Airports {


    @SerializedName("Airport")
    @Expose
    var airport: List<Airport>? = null

}
