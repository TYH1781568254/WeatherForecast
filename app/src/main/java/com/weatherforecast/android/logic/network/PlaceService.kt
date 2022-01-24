package com.weatherforecast.android.logic.network

import com.weatherforecast.android.WeatherForecastApplication
import com.weatherforecast.android.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceService {
    @GET("v2/place?token=${WeatherForecastApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query:String):Call<PlaceResponse>
}