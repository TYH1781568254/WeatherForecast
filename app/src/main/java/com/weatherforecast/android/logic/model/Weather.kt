package com.weatherforecast.android.logic.model

//通过Wrather类将Realtime和Daily对象进行封装
data class Weather(val realtime : RealtimeResponse.Realtime,val daily : DailyResponse.Daily)
