package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ScheduleResource {

    @SerializedName("Schedule")
    @Expose
    var schedule: List<Schedule>? = null
    @SerializedName("Meta")
    @Expose
    var meta: Meta? = null

}
