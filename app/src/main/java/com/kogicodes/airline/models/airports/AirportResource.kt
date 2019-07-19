package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AirportResource {
    @SerializedName("Airports")
    @Expose
    var airports: Airports? = null
    @SerializedName("Meta")
    @Expose
    var meta: Meta? = null
}
