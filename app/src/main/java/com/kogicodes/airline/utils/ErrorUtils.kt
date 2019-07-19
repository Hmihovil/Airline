
package com.kogicodes.airline.utils


import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response


class ErrorUtils {

    fun parseError(response: Response<*>): AppException {

        try {
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
            val erroBody: ErrorBody? = gson.fromJson(response.errorBody()!!.charStream(), type)




            return AppException(erroBody?.message, erroBody?.status_name, erroBody?.errors)

        } catch (e: Exception) {

            return AppException("Error Loading Data")

        }

    }
}