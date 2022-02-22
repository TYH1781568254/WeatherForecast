package com.weatherforecast.android.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.weatherforecast.android.WeatherForecastApplication
import com.weatherforecast.android.logic.model.Place

object PlaceDao {

    //将Place对象存储到SharedPreferences文件中
    fun savePlace(place: Place){
        sharedPreferences().edit{
            putString("place",Gson().toJson(place))
        }
    }

    //先将JSON字符串从SharedPreferences文件中提取出来，然后通过GSON将JSON字符串解析成Place对象并返回
    fun getSavedPlace():Place{
        val placejson = sharedPreferences().getString("place","")
        return Gson().fromJson(placejson,Place::class.java)
    }

    fun isPlaceSaved() = sharedPreferences().contains("place")

    private fun sharedPreferences() = WeatherForecastApplication.context.
            getSharedPreferences("weather_forecast",Context.MODE_PRIVATE)
}
