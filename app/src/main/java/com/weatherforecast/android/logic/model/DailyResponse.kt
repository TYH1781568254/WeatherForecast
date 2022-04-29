package com.weatherforecast.android.logic.model

import com.google.gson.annotations.SerializedName
import java.util.*

//在数据模型中用List集合来对JSON中的数组元素进行映射。
//这是未来几天天气信息的数据模型
data class DailyResponse(val status:String,val result: Result) {

    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temprature>, val skycon: List<Skycon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex)

    data class Temprature(val max: Float, val min: Float)

    data class Skycon(val value: String, val date: Date)

    data class LifeIndex(
        val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>)

    data class LifeDescription(val desc: String)
}