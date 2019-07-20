
import com.google.gson.GsonBuilder
import com.kogicodes.airline.network.EndPoints
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    fun getRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(getClient())
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

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .build()
            chain.proceed(newRequest)
        }.build()
    }


    fun getService(token: String?): EndPoints {
        return getRetrofit(token, BaseUrls().LIVE).create(EndPoints::class.java)
    }

    fun getService(): EndPoints {
        return getRetrofit(BaseUrls().LIVE).create(EndPoints::class.java)
    }



}
