package ru.alexander.maptest

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import okhttp3.ConnectionSpec
import java.util.Arrays.asList
import okhttp3.OkHttpClient


interface PositionApiService {

    @GET("/")
    fun search(): Observable<List<Point>>

    companion object Factory {
        fun create(): PositionApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://89.223.90.219:8080/")
                .build()

            return retrofit.create(PositionApiService::class.java)
        }
    }
}