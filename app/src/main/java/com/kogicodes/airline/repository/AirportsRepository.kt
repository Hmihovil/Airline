package com.kogicodes.airline.repository


import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.models.airports.AirportModel
import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.utils.AppException
import com.kogicodes.airline.utils.ErrorUtils
import com.kogicodes.airline.utils.FailureUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class AirportsRepository(application: Application) {
     val airPortsObservable = MutableLiveData<Resource<AirportsModel>>()
    val airPortObservable = MutableLiveData<Resource<List<AirportModel>>>()
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)

    private val context: Context
    private var page: Int=0

    init {
        context = application.applicationContext
    }

    fun getAirports(nextPage: Boolean) {
        setIsLoading(Observable.AIRPORTS)
        if (NetworkUtils.isConnected(context)) {
            when {
                nextPage -> {
                }
                else -> page = 0
            }
            executeAirports(Observable.AIRPORTS, page)

        } else {
            setNetworkError(Observable.AIRPORTS)
        }
    }

    fun getAirport(codes: MutableList<String?>) {


        setIsLoading(Observable.AIRPORT)
        if (NetworkUtils.isConnected(context)) {

            executeAirport(Observable.AIRPORT, codes)

        } else {
            setNetworkError(Observable.AIRPORT)
        }
    }


    private fun createCallBody(lastPage: Int): Map<String, String> {

        val hashMap = HashMap<String, String>()
        hashMap["limit"] = "100"
        hashMap["offset"] = "" + lastPage
        hashMap["LHoperated"] = "0"
        hashMap["lang"] = "en"
        return hashMap
    }

    private fun createAirportCallBody(): Map<String, String> {

        val hashMap = HashMap<String, String>()
        hashMap["lang"] = "en"
        return hashMap
    }

    private fun executeAirports(observable: Observable, page: Int) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(prefrenceManager.token.accessToken).getAirports(createCallBody(page))
            call.enqueue(object : Callback<AirportsModel> {
                override fun onFailure(call: Call<AirportsModel>?, t: Throwable?) {
                    onFailure(observable, t, FailureUtils().parseError(call, t))
                }
                override fun onResponse(call: Call<AirportsModel>?, response: Response<AirportsModel>?) {
                    onResponse(response, observable)
                }
            })
        }
    }

    private fun executeAirport(observable: Observable, codes: MutableList<String?>) {


        val requests: MutableList<io.reactivex.Observable<AirportModel>> = ArrayList()
        for (code in codes) {
            requests.add(
                RequestService.getService(prefrenceManager.token.accessToken).getAirport(
                    code!!,
                    createAirportCallBody()
                )
            )
        }




        io.reactivex.Observable.zip(
            requests
        ) { objects ->
            val dataaResponses: MutableList<AirportModel> = ArrayList()
            for (o in objects) {

                var data: AirportModel = o as AirportModel

                dataaResponses.add(data)
            }
            dataaResponses
        }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                Consumer<List<AirportModel>> { dataaResponses -> onResponseAirport(dataaResponses, observable) },

                Consumer<Throwable> { e -> setIsError(observable, e.toString(), AppException("")) }
            )


    }

   

    private fun setNetworkError(observable: Observable) {
        setIsError(
            observable, context.getString(com.kogicodes.airline.R.string.network_issue_message),
            AppException(context.getString(com.kogicodes.airline.R.string.network_issue_message))
        )
    }

    private fun onFailure(observable: Observable, t: Throwable?, agriException: AppException) {
        setIsError(observable, t.toString(), agriException)
    }

    private fun onResponse(response: Response<AirportsModel>?, observable: Observable) {

        if (response != null) {
            if (response.isSuccessful) {
                setIsSuccesful(observable, response.body())

                val totalCount = response.body()!!.airportResource?.meta?.totalCount
                val next = page + 100
                if (next < totalCount!!) {
                    page=next
                } else {

                }

            } else {
                setIsError(observable, "", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable, "", AppException("Error Loading Data"))
        }
    }

    private fun onResponseAirport(response: List<AirportModel>?, observable: Observable) {

        if (response != null) {
            setIsSuccesful(observable, response)


        } else {
            setIsError(observable, "", AppException("Error Loading Data"))
        }
    }


    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.loading(null))
            Observable.AIRPORT -> airPortObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.success(data as AirportsModel))
            Observable.AIRPORT -> airPortObservable.postValue(Resource.success(data as List<AirportModel>))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.error(message, null, exception))
            Observable.AIRPORT -> airPortObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        AIRPORTS,
        AIRPORT
    }

 
}
