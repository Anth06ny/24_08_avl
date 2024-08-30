package com.example.a24_08_avl.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a24_08_avl.model.WeatherAPI
import com.example.a24_08_avl.model.WeatherBean

data class PictureBean(val id: Int, val url: String, val title: String, val longText: String)

const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""


fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")
    println(viewModel.dataList)
}

class MainViewModel : ViewModel() {
    var dataList: List<PictureBean> = ArrayList()
    var errorMessage = ""
    var runInProgress = false

    fun loadWeathers(cityName: String) {

        val list: List<WeatherBean> = WeatherAPI.loadWeathers(cityName)
        dataList = list.map {
            PictureBean(
                it.id.toInt(),
                "https://openweathermap.org/img/wn/01d@4x",
                it.name,
                "Il fait ${it.main.temp}° à ${it.name} "
            )
        }
    }


    init {//Création d'un jeu de donnée au démarrage
        loadFakeData()
    }

    fun loadFakeData() {
        dataList = ArrayList(
            listOf(
                PictureBean(1, "https://picsum.photos/200", "ABCD", LONG_TEXT),
                PictureBean(2, "https://picsum.photos/201", "BCDE", LONG_TEXT),
                PictureBean(3, "https://picsum.photos/202", "CDEF", LONG_TEXT),
                PictureBean(4, "https://picsum.photos/203", "EFGH", LONG_TEXT)
            ).shuffled()
        ) //shuffled() pour avoir un ordre différent à chaque appel
    }

}