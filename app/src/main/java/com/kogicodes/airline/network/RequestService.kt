/*
 * *
 *  * Created by Kogi Eric  on 5/17/19 8:29 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Last modified 5/17/19 8:24 AM
 *
 */

import com.google.gson.GsonBuilder
import com.kogicodes.airline.network.EndPoints
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author kogi
 */
object RequestService {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun getRetrofit(token: String?, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient(token))
            .build()
    }



    private fun getClient(token: String?): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()
    }



    fun getService(token: String?): EndPoints {
        return getRetrofit(token, BaseUrls().LIVE).create(EndPoints::class.java)
    }




}
