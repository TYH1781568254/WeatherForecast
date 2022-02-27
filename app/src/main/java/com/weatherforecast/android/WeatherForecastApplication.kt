package com.weatherforecast.android

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

//此类给项目提供获取全局Context的方式
class WeatherForecastApplication : Application() {

    companion object {
        const val TOKEN = "4dwNEj85XoMW7gES"//彩云天气的令牌值
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}