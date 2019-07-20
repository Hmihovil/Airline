package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Names : Serializable {

    @SerializedName("Name")
    @Expose
    var name: Name? = null

}
