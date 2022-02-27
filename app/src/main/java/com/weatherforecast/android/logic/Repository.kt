package com.weatherforecast.android.logic


import androidx.lifecycle.liveData
import com.weatherforecast.android.logic.dao.PlaceDao
import com.weatherforecast.android.logic.model.Place
import com.weatherforecast.android.logic.model.Weather
import com.weatherforecast.android.logic.network.WeatherForecastNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.RuntimeException
import kotlin.coroutines.CoroutineContext

object Repository {

    fun searchPlaces(query:String) = fire(Dispatchers.IO) {
//        val result =try {
            val placeResponse = WeatherForecastNetwork.searchPlaces(query)
        //判断响应状态，为ok则对获取的城市数据列表进行包装
        //否则包装一个异常信息
            if (placeResponse.status == "ok"){
                val places = placeResponse.places
                Result.success(places)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
//        }catch (e:Exception){
//            Result.failure<List<Place>>(e)
//        }
//        emit(result)//将我们包装的信息发射出去
    }

    fun refreshWeather(lng:String,lat:String)= fire(Dispatchers.IO) {
//        val result= try {
            coroutineScope {
                val deferredRealtime= async {
                    WeatherForecastNetwork.getRealtimeWeather(lng, lat)
                }
                val deferredDaily= async {
                    WeatherForecastNetwork.getDailyWeather(lng, lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                //实时天气信息API与未来天气信息API的服务器都返回ok才进行信息包装
                //否则包装一个异常信息
                if (realtimeResponse.status == "ok" && dailyResponse.status == "ok"){
                    val weather = Weather(realtimeResponse.result.realtime,
                                            dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(
                        RuntimeException(
                            "realtime response status is ${realtimeResponse.status}"+
                        "daily response status is ${dailyResponse.status}"
                        )
                    )
                }
            }
//        }catch (e:Exception){
//            Result.failure<Weather>(e)
//        }
//        emit(result)
    }

    private fun <T> fire(context:CoroutineContext,block: suspend() -> Result<T>)=
            liveData<Result<T>> (context){
                val result = try {
                    block()
                }catch (e:Exception){
                    Result.failure<T>(e)
                }
                emit(result)//将我们包装的信息发射出去
            }

    fun savePlace(place: Place) = PlaceDao.savePlace(place)

    fun getSavedPlace() = PlaceDao.getSavedPlace()

    fun isPlaceSaved() = PlaceDao.isPlaceSaved()
}