package com.weatherforecast.android.logic.model

import com.google.gson.annotations.SerializedName

//将数据类定义为内部类，可以防止出现和其他接口的数据模型有同名冲突的情况
//这里是实时天气数据的数据模型
data class RealtimeResponse (val status:String,val result: Result) {

    data class Result(val realtime: Realtime)

    data class Realtime(
        val skycon: String, val temperature: Float,
        @SerializedName("air_quality") val airQuality: AirQuality
    )

    data class AirQuality(val aqi: AQI)

    data class AQI(val chn: Float)
}