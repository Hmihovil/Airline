package com.kogicodes.airline.repository


import RequestService
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.R
import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.utils.AppException
import com.kogicodes.airline.utils.ErrorUtils
import com.kogicodes.airline.utils.FailureUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class AirportsRepository(application: Application) {
     val airPortsObservable = MutableLiveData<Resource<AirportsModel>>()
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)

    private val context: Context
    private var page: Int=0

    init {
        context = application.applicationContext
    }

    fun getAirports() {
        setIsLoading(Observable.AIRPORTS)
        if (NetworkUtils.isConnected(context)) {
            executeAirports(Observable.AIRPORTS)
        } else {
            setNetworkError(Observable.AIRPORTS)
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

    private fun executeAirports(observable: Observable) {
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

   

    private fun setNetworkError(observable: Observable) {
        setIsError(
            observable, context.getString(R.string.network_issue_message),
            AppException(context.getString(R.string.network_issue_message))
        )
    }

    private fun onFailure(observable: Observable, t: Throwable?, agriException: AppException) {

        setIsError(observable, t.toString(), agriException)
    }

    private fun onResponse(response: Response<AirportsModel>?, observable: Observable) {

        if (response != null) {
            if (response.isSuccessful) {
                setIsSuccesful(observable, response)

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


    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.success(data as AirportsModel))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.AIRPORTS -> airPortsObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        AIRPORTS
    }

 
}
