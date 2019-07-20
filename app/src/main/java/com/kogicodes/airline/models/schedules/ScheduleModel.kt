package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ScheduleModel : Serializable {

    @SerializedName("ScheduleResource")
    @Expose
    var scheduleResource: ScheduleResource? = null

}