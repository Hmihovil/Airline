package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class ScheduledTimeLocal {

    @SerializedName("DateTime")
    @Expose
    var dateTime: String? = null

    val date: DateTime
        get() {
            val formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss")
            return formatter.parseDateTime(dateTime!!)

        }

}
