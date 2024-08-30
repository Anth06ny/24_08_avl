package com.example.a24_08_avl.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide.init
import com.example.a24_08_avl.BuildConfig
import com.example.a24_08_avl.model.WeatherAPI
import com.example.a24_08_avl.model.WeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class PictureBean(val id: Int, val url: String, val title: String, val longText: String)

const val LONG_TEXT = """Le Lorem Ipsum est simplement du faux texte employé dans la composition
    et la mise en page avant impression. Le Lorem Ipsum est le faux texte standard
    de l'imprimerie depuis les années 1500"""


fun main() {
    val viewModel = MainViewModel()
    viewModel.loadWeathers("Nice")
    while(viewModel.runInProgress) {
        Thread.sleep(300)
    }
    println("List : " + viewModel.dataList)
    println("Erreur : " + viewModel.errorMessage)
}

class MainViewModel : ViewModel() {
    var dataList by mutableStateOf<List<PictureBean>>(emptyList())
    var errorMessage by mutableStateOf("")
    var runInProgress by mutableStateOf(false)

    init {
        if(BuildConfig.DEBUG) {
            loadFakeData()
        }
    }

    fun loadWeathers(cityName: String) {

        runInProgress = true
        errorMessage = ""

        viewModelScope.launch(Dispatchers.Default) {

            try {
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
            catch(e:Exception) {
                //Afficher l'exception dans la console
                e.printStackTrace()
                errorMessage = "Une erreur est survenue : ${e.message}"
            }

            runInProgress = false
        }
    }


    init {//Création d'un jeu de donnée au démarrage
        //loadFakeData()
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