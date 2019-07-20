package com.kogicodes.airline.models.schedules


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Details : Serializable {

    @SerializedName("Stops")
    @Expose
    var stops: Stops? = null
    @SerializedName("DaysOfOperation")
    @Expose
    var daysOfOperation: Int? = null
    @SerializedName("DatePeriod")
    @Expose
    var datePeriod: DatePeriod? = null

}