package com.kogicodes.airline.network


import com.kogicodes.airline.models.airports.AirportsModel
import com.kogicodes.airline.models.oauth.Token
import com.kogicodes.airline.models.schedules.ScheduleModel
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author kogi
 */
interface EndPoints {
    @FormUrlEncoded
    @POST("oauth/token")
    fun oauthToken(@Field("client_id") clientId: String,
                   @Field("client_secret") clientSecret: String,
                   @Field("grant_type") grantType: String): Call<Token>


    @GET("references/airports")
    fun getAirports(@QueryMap params: Map<String, String>): Call<AirportsModel>


    @GET("operations/schedules/{origin}/{destination}/{fromDateTime}")
    fun getSchedules(
            @Path("origin") origin: String,
            @Path("destination") destination: String,
            @Path("fromDateTime") fromDateTime: String): Call<ScheduleModel>

}
