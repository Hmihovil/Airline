package com.kogicodes.airline.models.schedules

import java.io.Serializable

class ScheduleSearch : Serializable {

    var origin: String=""
    var destination: String=""
    var fromDateTime: String=""

    constructor(origin: String, destination: String, fromDateTime: String) {
        this.origin = origin
        this.destination = destination
        this.fromDateTime = fromDateTime
    }
}