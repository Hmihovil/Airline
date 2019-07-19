package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Link {


    @SerializedName("@Href")
    @Expose
    var href: String? = null
    @SerializedName("@Rel")
    @Expose
    var rel: String? = null

}
