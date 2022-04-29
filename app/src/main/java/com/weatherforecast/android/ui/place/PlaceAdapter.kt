package com.weatherforecast.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.weatherforecast.android.R
import com.weatherforecast.android.logic.model.Place
import com.weatherforecast.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.activity_weather.*

//为RecyclerView准备配适器，使用RecyclerView配适器标准写法，让配适器继承自RecyclerView.Adapter
//并将泛型指定为PlaceAdapter.ViewHolder
class PlaceAdapter(private val fragment: PlaceFragment,private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>(){

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_item,
            parent,false)
        val holder = ViewHolder(view)
        //给place_item.xml的最外层布局注册一个点击事件监听器
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]

            //判断PlaceFragment所处的Activity，如果在WeatherActivity
            // 就关闭滑动菜单，给WeatherViewModel传入新的经纬度和地区名，然后刷新天气信息
            val activity = fragment.activity
            if (activity is WeatherActivity){
                activity.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.location.lng
                activity.viewModel.locationLat = place.location.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            }else {
                // 获取当前点击项的经纬度及地区名称传入Intent
                // 然后利用startActivity启动WeatherActivity
                val intent = Intent(parent.context, WeatherActivity::class.java).apply {
                    putExtra("location_lng", place.location.lng)
                    putExtra("location_lat", place.location.lat)
                    putExtra("place_name", place.name)
                }
//            fragment.viewModel.savePlace(place)
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)//储存选中的城市
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    override fun getItemCount() = placeList.size

}