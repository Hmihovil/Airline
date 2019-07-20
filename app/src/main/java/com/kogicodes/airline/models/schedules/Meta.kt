package com.kogicodes.airline.models.schedules

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Meta : Serializable {

    @SerializedName("@Version")
    @Expose
    var version: String? = null
    @SerializedName("Link")
    @Expose
    var link: List<Link>? = null

}
