package com.kogicodes.airline.repository


import RequestService
import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kogicodes.airline.R
import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.basic.Resource
import com.kogicodes.airline.models.oauth.Token
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

class TokenRepository(application: Application) {
     val tokenObservable = MutableLiveData<Resource<Token>>()
    private val prefrenceManager: PrefrenceManager = PrefrenceManager(application)

    private val context: Context
    private var page: Int=0

    init {
        context = application.applicationContext
    }

    fun getToken() {
        setIsLoading(Observable.TOKEN)
        if (NetworkUtils.isConnected(context)) {
            executeAuth(Observable.TOKEN)
        } else {
            setNetworkError(Observable.TOKEN)
        }
    }


    private fun executeAuth(observable: Observable) {
        GlobalScope.launch(context = Dispatchers.Main) {
            val call = RequestService.getService(prefrenceManager.token.accessToken).oauthToken(context.getString(R.string.Key),context.getString(R.string.Secret),context.getString(R.string.grant_type))
            call.enqueue(object : Callback<Token> {
                override fun onFailure(call: Call<Token>?, t: Throwable?) {
                    onFailure(observable, t, FailureUtils().parseError(call, t))
                }
                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
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

    private fun onResponse(response: Response<Token>?, observable: Observable) {

        if (response != null) {
            if (response.isSuccessful) {
                setIsSuccesful(observable, response)



            } else {
                setIsError(observable, "", ErrorUtils().parseError(response))
            }
        } else {
            setIsError(observable, "", AppException("Error Loading Data"))
        }
    }


    private fun setIsLoading(observable: Observable) {
        when (observable) {
            Observable.TOKEN -> tokenObservable.postValue(Resource.loading(null))
        }
    }

    private fun <T> setIsSuccesful(observable: Observable, data: T?) {
        when (observable) {
            Observable.TOKEN -> tokenObservable.postValue(Resource.success(data as Token))
        }

    }

    private fun setIsError(observable: Observable, message: String, exception: AppException) {
        when (observable) {
            Observable.TOKEN -> tokenObservable.postValue(Resource.error(message, null, exception))
        }
    }

    enum class Observable {
        TOKEN
    }

 
}
