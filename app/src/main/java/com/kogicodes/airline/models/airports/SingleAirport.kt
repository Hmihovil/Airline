package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SingleAirport {


    @SerializedName("Airport")
    @Expose
    var airport: Airport? = null

}
