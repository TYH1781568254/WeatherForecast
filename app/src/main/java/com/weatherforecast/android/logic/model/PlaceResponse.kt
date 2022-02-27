package com.weatherforecast.android.logic.model


import com.google.gson.annotations.SerializedName

//此处定义的类与属性都是按照返回的JSON格式来定义的
data class PlaceResponse(val status:String,val places:List<Place>)

data class Place(val name:String,val location:Location,
            @SerializedName("formatted_address") val address:String)
            //通过注解让JSON字段和Kotlin字段之间建立映射关系

data class Location(val lng:String,val lat:String)