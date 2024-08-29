package com.example.a24_08_avl.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a24_08_avl.model.CarBean
import com.example.a24_08_avl.model.WeatherAPI
import com.example.a24_08_avl.model.WeatherBean

data class PictureBean(val id:Int, val url: String, val title: String, val longText: String)

fun main(){
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")
    println(viewModel.dataList)
}

class MainViewModel : ViewModel() {
    var dataList : ArrayList<PictureBean> = ArrayList()

    fun loadWeathers(cityName:String){
        val list: List<WeatherBean> = WeatherAPI.loadWeathers(cityName)

        for(city in list) {
            var picture = PictureBean(
                city.id.toInt(),
                "https://openweathermap.org/img/wn/01d@4x",
                city.name,
                "Il fait ${city.main.temp}° à ${city.name} "
            )
            dataList.add(picture)
        }
    }
}