package com.kogicodes.airline.repository


import NetworkUtils
import RequestService
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.R
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.models.schedules.ScheduleModel
import com.kogicodes.airline.models.schedules.ScheduleSearch
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

class ScheduleRepository(application: Application) {
     val scheduleObservable = MutableLiveData<Resource<ScheduleModel>>()
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)

    private val context: Context

    init {
        context = application.applicationContext
    }

    fun getSchedules(search : ScheduleSearch) {
        setIsLoading(Observable.SCHEDULES)
        if (NetworkUtils.isConnected(context)) {
            executeSchedules(Observable.SCHEDULES,search)
        } else {
            setNetworkError(Observable.SCHEDULES)
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

    private fun executeSchedules(observable: Observable,search: ScheduleSearch) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(prefrenceManager.token.accessToken).getSchedules(destination = search.destination,origin = search.origin,fromDateTime = search.fromDateTime)
            call.enqueue(object : Callback<ScheduleModel> {
                override fun onFailure(call: Call<ScheduleModel>?, t: Throwable?) {
                    onFailure(observable, t, FailureUtils().parseError(call, t))
                }
                override fun onResponse(call: Call<ScheduleModel>?, response: Response<ScheduleModel>?) {
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

    private fun onResponse(response: Response<ScheduleModel>?, observable: Observable) {

        if (response != null) {
            if (response.isSuccessful) {

                setIsSuccesful(observable, response.body())
            } else {
                setIsError(observable, "", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable, "", AppException("Error Loading Data"))
        }
    }


    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.SCHEDULES -> scheduleObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.SCHEDULES -> scheduleObservable.postValue(Resource.success(data as ScheduleModel))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.SCHEDULES -> scheduleObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        SCHEDULES
    }

 
}
