
package com.kogicodes.airline.utils

internal class ErrorBody {

    var status_code: Int? = 0
    var status_name: String? = "Error"
    var success: Boolean? = false
    var message: String? = "Error"
    var result_code: Int? = 0
    var errors: List<String>? = null
    var error: String? = null

}