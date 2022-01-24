package com.weatherforecast.android.logic


import androidx.lifecycle.liveData
import com.weatherforecast.android.logic.model.Place
import com.weatherforecast.android.logic.network.WeatherForecastNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.RuntimeException

object Repository {

    fun searchPlaces(query:String) = liveData(Dispatchers.IO) {
        val result =try {
            val placeResponse = WeatherForecastNetwork.searchPlaces(query)
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e:Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}