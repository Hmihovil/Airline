package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class DatePeriod {

    @SerializedName("Effective")
    @Expose
    var effective: String? = null
    @SerializedName("Expiration")
    @Expose
    var expiration: String? = null

}
