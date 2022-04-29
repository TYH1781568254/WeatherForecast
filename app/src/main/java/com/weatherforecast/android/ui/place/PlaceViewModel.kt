package com.weatherforecast.android.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.weatherforecast.android.logic.Repository
import com.weatherforecast.android.logic.model.Place

//城市搜索功能的ViewModel层，即逻辑层与UI层的桥梁
class PlaceViewModel : ViewModel() {

    //将传入的搜索参数传入searchLiveData对象
    private val searchLiveData = MutableLiveData<String>()

    //该集合用来缓存城市数据
    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchPlaces(query)
    }
    //使得调用仓库层的searchPlace（）就能发起网络请求，
    // 并且将仓库层返回的LiveData对象转换成一个可供activity观察的LiveData对象
    fun searchPlaces(query:String){
        searchLiveData.value = query
    }

    //对Repository中封装的PlaceDao中的方法再进行一层封装
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun getSavePlace() = Repository.getSavedPlace()

    fun isPlaceSaved() = Repository.isPlaceSaved()
}