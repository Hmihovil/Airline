package com.kogicodes.airline.ui


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.models.airports.Airport
import com.kogicodes.airline.models.airports.AirportModel
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
    private val airPortObservable = MediatorLiveData<Resource<List<AirportModel>>>()
    private val tokenObservable = MediatorLiveData<Resource<Token>>()

    val originAirportData: MutableLiveData<Airport> by lazy {
        MutableLiveData<Airport>()
    }

    val destinationAirportData: MutableLiveData<Airport> by lazy {
        MutableLiveData<Airport>()
    }

    init {


        scheduleObservable.addSource(scheduleRepository.scheduleObservable) { data -> scheduleObservable.setValue(data) }
        airPortsObservable.addSource(airportsRepository.airPortsObservable) { data -> airPortsObservable.setValue(data) }
        airPortObservable.addSource(airportsRepository.airPortObservable) { data -> airPortObservable.setValue(data) }
        tokenObservable.addSource(tokenRepository.tokenObservable) { data -> tokenObservable.setValue(data) }


    }


    fun airports(netPage: Boolean) {
        airportsRepository.getAirports(netPage)
    }

    fun observeAirports(): LiveData<Resource<AirportsModel>> {
        return airPortsObservable
    }

    fun airport(code: MutableList<String?>) {
        airportsRepository.getAirport(code)
    }

    fun observeAirport(): LiveData<Resource<List<AirportModel>>> {
        return airPortObservable
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
