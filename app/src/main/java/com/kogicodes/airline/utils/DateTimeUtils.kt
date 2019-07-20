package com.kogicodes.airline.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtils {

    val today: String
        get() {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = Date()

            return dateFormat.format(date)

        }


    fun getDateAndTime(date1q: String): String {

        // 2019-01-10T10:10
        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm")
        val output = SimpleDateFormat("dd/MM/yyyy HH:mm")

        var d: Date? = null
        try {
            d = input.parse(date1q)
            return output.format(d!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }


        return ""

    }
}
