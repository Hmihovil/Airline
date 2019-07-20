package com.kogicodes.airline.models.schedules


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Link : Serializable {

    @SerializedName("@Href")
    @Expose
    var href: String? = null
    @SerializedName("@Rel")
    @Expose
    var rel: String? = null

}
