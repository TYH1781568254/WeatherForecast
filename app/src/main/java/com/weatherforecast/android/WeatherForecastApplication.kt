package com.weatherforecast.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class WeatherForecastApplication : Application() {

    companion object {
        const val TOKEN = "4dwNEj85XoMW7gES"
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}