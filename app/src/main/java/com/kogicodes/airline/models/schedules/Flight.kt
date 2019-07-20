package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Flight : Serializable {

    @SerializedName("Departure")
    @Expose
    var departure: Departure? = null
    @SerializedName("Arrival")
    @Expose
    var arrival: Arrival? = null
    @SerializedName("MarketingCarrier")
    @Expose
    var marketingCarrier: MarketingCarrier? = null
    @SerializedName("OperatingCarrier")
    @Expose
    var operatingCarrier: OperatingCarrier? = null
    @SerializedName("Equipment")
    @Expose
    var equipment: Equipment? = null
    @SerializedName("Details")
    @Expose
    var details: Details? = null

}