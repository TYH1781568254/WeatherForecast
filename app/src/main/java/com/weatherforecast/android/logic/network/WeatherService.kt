package com.weatherforecast.android.logic.network

import com.weatherforecast.android.WeatherForecastApplication
import com.weatherforecast.android.logic.model.DailyResponse
import com.weatherforecast.android.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

//访问天气信息API的Retrofit接口，@GET注解用来声明要访问的API接口，
// @Path注解来向请求接口中动态传入经纬度的坐标
interface WeatherService {
    //该方法用来获取实时的天气信息
    @GET("v2.5/${WeatherForecastApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat:String):
            Call<RealtimeResponse>

    //该方法用来获取未来天气信息
    @GET("v2.5/${WeatherForecastApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng")lng: String,@Path("lat") lat: String):
            Call<DailyResponse>
}