package com.kogicodes.airline.ui


import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.models.oauth.Token
import com.kogicodes.airline.models.schedules.ScheduleModel
import com.kogicodes.airline.models.schedules.ScheduleSearch
import com.kogicodes.airline.repository.AirportsRepository
import com.kogicodes.airline.repository.ScheduleRepository
import com.kogicodes.airline.repository.TokenRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private var airportsRepository: AirportsRepository = AirportsRepository(application)
    private var scheduleRepository: ScheduleRepository = ScheduleRepository(application)
    private var tokenRepository: TokenRepository = TokenRepository(application)




    private val scheduleObservable = MediatorLiveData<Resource<ScheduleModel>>()
    private val airPortsObservable = MediatorLiveData<Resource<AirportsModel>>()
    private val tokenObservable = MediatorLiveData<Resource<Token>>()


    init {


        scheduleObservable.addSource(scheduleRepository.scheduleObservable) { data -> scheduleObservable.setValue(data) }
        airPortsObservable.addSource(airportsRepository.airPortsObservable) { data -> airPortsObservable.setValue(data) }
        tokenObservable.addSource(tokenRepository.tokenObservable) { data -> tokenObservable.setValue(data) }


    }


    fun airports() {
        airportsRepository.getAirports()
    }

    fun observeAirports(): LiveData<Resource<AirportsModel>> {
        return airPortsObservable
    }


    fun schedules(parameters: ScheduleSearch) {
        scheduleRepository.getSchedules(parameters)
    }

    fun observeSchedules(): LiveData<Resource<ScheduleModel>> {
        return scheduleObservable
    }

    fun token() {
        tokenRepository.getToken()
    }

    fun observeToken(): LiveData<Resource<Token>> {
        return tokenObservable
    }


}
