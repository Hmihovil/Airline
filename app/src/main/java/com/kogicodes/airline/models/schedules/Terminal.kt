package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Terminal : Serializable {

    @SerializedName("Name")
    @Expose
    var name: String? = null
        get() {
            if (field == null) {
                this.name = ""
            }
            return field
        }

}
