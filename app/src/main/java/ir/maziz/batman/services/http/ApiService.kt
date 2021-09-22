package ir.maziz.batman.services.http

import io.reactivex.Single
import ir.maziz.batman.data.BatmanResponse
import ir.maziz.batman.data.DetailBatmanResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(".")
    fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") search: String
    ): Single<BatmanResponse>

    @GET(".")
    fun getDetail(
        @Query("apikey") apiKey: String,
        @Query("i") imdbId: String
    ): Single<DetailBatmanResponse>
}

fun createApiServiceInstance(): ApiService {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://www.omdbapi.com/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(ApiService::class.java)
}