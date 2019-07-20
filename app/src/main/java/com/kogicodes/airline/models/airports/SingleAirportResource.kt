package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SingleAirportResource {
    @SerializedName("Airports")
    @Expose
    var airports: SingleAirport? = null
    @SerializedName("Meta")
    @Expose
    var meta: Meta? = null
}
