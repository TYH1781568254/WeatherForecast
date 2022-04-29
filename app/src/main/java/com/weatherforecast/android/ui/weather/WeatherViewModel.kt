package com.weatherforecast.android.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.weatherforecast.android.logic.Repository
import com.weatherforecast.android.logic.model.Location

//天气信息显示功能的ViewModel层，即逻辑层与UI层的桥梁
class WeatherViewModel: ViewModel() {

    private val locationLiveData = MutableLiveData<Location>()

    var locationLng=""

    var locationLat=""

    var placeName=""

    //使用Transformations的switchMap()方法来观察locationLiveData对象
    //并在switchMap()方法的装换函数中调用仓库层的refreshWeather()方法
    val weatherLiveData = Transformations.switchMap(locationLiveData){ location ->
        Repository.refreshWeather(location.lng,location.lat)
    }

    //refreshWeather()方法用来刷新天气信息，接收经纬度参数封装成一个Location对象
    //然后赋值给locationLiveData对象
    fun refreshWeather(lng:String,lat:String){
        locationLiveData.value = Location(lng,lat)
    }
}