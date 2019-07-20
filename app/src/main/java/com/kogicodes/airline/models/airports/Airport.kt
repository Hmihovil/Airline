package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Airport : Serializable {
    @SerializedName("AirportCode")
    @Expose
    var airportCode: String? = null
    @SerializedName("Position")
    @Expose
    var position: Position? = null
    @SerializedName("CityCode")
    @Expose
    var cityCode: String? = null
    @SerializedName("CountryCode")
    @Expose
    var countryCode: String? = null
    @SerializedName("LocationType")
    @Expose
    var locationType: String? = null
    @SerializedName("Names")
    @Expose
    var names: Names? = null
    @SerializedName("UtcOffset")
    @Expose
    var utcOffset: String? = null
    @SerializedName("TimeZoneId")
    @Expose
    var timeZoneId: String? = null


    constructor(position: Position?, names: Names?) {
        this.position = position
        this.names = names
    }
}

