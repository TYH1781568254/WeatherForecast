package com.weatherforecast.android.ui.place

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.weatherforecast.android.MainActivity
import com.weatherforecast.android.R
import com.weatherforecast.android.ui.weather.WeatherActivity
import kotlinx.android.synthetic.main.fragment_place.*
import kotlinx.android.synthetic.*
import java.util.*

//搜索城市数据模块的Fragment代码编写，完成之后要将该Fragment添加到activity_main.xml中
class PlaceFragment : Fragment() {
    //获取PlaceViewModel的实例
    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    //加入编写的fragment_place布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //如果当前已有储存的城市数据，就获取数据并解析为place对象，然后使用它的经纬度和地名
        // 直接跳转传递给WeatherActivity
        if (activity is MainActivity && viewModel.isPlaceSaved()){
            val place = viewModel.getSavePlace()
            val intent = Intent(context,WeatherActivity::class.java).apply {
                putExtra("location_lng",place.location.lng)
                putExtra("location_lat",place.location.lat)
                putExtra("place_name",place.name)
            }
            startActivity(intent)
            activity?.finish()
            return
        }

        //给RecyclerView设置LayoutManager和配适器，并使用PlaceViewModel中placeList集合作为数据源
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this,viewModel.placeList)
        recyclerView.adapter = adapter
//        searchPlaceEdit.addTextChangedListener{ editable->
//            val content = editable.toString()
//            if (content.isNotEmpty()){
//                viewModel.searchPlaces(content)
//            }else{
//                recyclerView.visibility = View.GONE
//                bgImageView.visibility = View.VISIBLE
//                viewModel.placeList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }

//        searchPlaceEdit.addTextChangedListener(new TextWatcher()){ editable->
//            val content = editable.toString()
//            if (content.isNotEmpty()){
//                viewModel.searchPlaces(content)
//            }else{
//                recyclerView.visibility = View.GONE
//                bgImageView.visibility = View.VISIBLE
//                viewModel.placeList.clear()
//                adapter.notifyDataSetChanged()
//            }
//        }

        //addTextChangedListener（）该方法用于监听搜索框内容变化情况
       searchPlaceEdit.addTextChangedListener( object :TextWatcher{
           override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//               val content=s.toString()
//               if (content.isNotEmpty()){
//                   viewModel.searchPlaces(content)
//               }else{
//                   recyclerView.visibility = View.GONE
//                   recyclerView.visibility = View.VISIBLE
//                   viewModel.placeList.clear()
//                   adapter.notifyDataSetChanged()
//               }
           }

           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//               val content=s.toString()
//               if (content.isNotEmpty()){
//                   viewModel.searchPlaces(content)
//               }else{
//                   recyclerView.visibility = View.GONE
//                   recyclerView.visibility = View.VISIBLE
//                   viewModel.placeList.clear()
//                   adapter.notifyDataSetChanged()
//               }

           }

           //该重写方法表示在文字发送改变后进行操作
           override fun afterTextChanged(s: Editable?) {
               val content=s.toString()
               if (content.isNotEmpty()){
                   //输入框内容不为空，发起搜索城市数据网络请求
                   viewModel.searchPlaces(content)
               }else{
                   //内容为空，隐藏RecyclerView，显示背景图
                   recyclerView.visibility = View.GONE
                   recyclerView.visibility = View.VISIBLE
                   viewModel.placeList.clear()
                   adapter.notifyDataSetChanged()
               }
           }

       })

        //对PlaceViewModel中的placeLiveData对象进行观察当出现任何数据变化时，
        // 就会回调到传入的Observer接口实现中
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer { result ->
            val places = result.getOrNull()
            //数据不为空时，将数据添加到PlaceViewModel的placeList集合中，
            // 数据为空弹出异常提示
            if (places != null){
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            }else{
                Toast.makeText(activity,"未能查询到任何地点",Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })

    }
}


