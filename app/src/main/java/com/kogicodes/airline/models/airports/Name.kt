package com.kogicodes.airline.models.airports

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Name : Serializable {
    @SerializedName("@LanguageCode")
    @Expose
    var languageCode: String? = null
    @SerializedName("$")
    @Expose
    var `$`: String? = null

}
