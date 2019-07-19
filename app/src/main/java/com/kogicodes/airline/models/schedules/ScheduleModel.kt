package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleModel {

    @SerializedName("ScheduleResource")
    @Expose
    var scheduleResource: ScheduleResource? = null

}