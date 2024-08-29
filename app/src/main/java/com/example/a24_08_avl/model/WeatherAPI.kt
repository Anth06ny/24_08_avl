package com.example.a24_08_avl.model

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

//Utilisation
fun main() {
//    //Lance la requête et met le corps de la réponse dans html
//    var html : String = WeatherAPI.sendGet("https://www.google.fr")
//    //Affiche l'HTML
//    println("html=$html")

    val list: List<WeatherBean> = WeatherAPI.loadWeathers("Nice")
    for (city in list) {
        println("Il fait ${city.main.temp}° à ${city.name} (id=${city.id}) avec un vent de ${city.wind.speed} m/s)")
        println("-Description : ${city.weather.getOrNull(0)?.description ?: "-"}")
        println("-Icône : ${city.weather.getOrNull(0)?.icon ?: "-"}")
    }

    println(list)
//    var user = WeatherAPI.loadRandomUser()
//
//    println("Il s'appelle ${user.name} pour le contacter :")
//    println("Phone : ${user.coord?.phone ?: "-"}")
//    println("Mail : ${user.coord?.mail ?: "-"}")
}

object WeatherAPI {
    val client = OkHttpClient()
    val gson = Gson()

    fun loadWeathers(cityname: String): List<WeatherBean> {
        val json = sendGet("https://api.openweathermap.org/data/2.5/find?q=$cityname&cnt=5&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        val res = gson.fromJson(json, WeatherAroundBean::class.java)
        return res.list
    }

    fun loadRandomUser(): UserBean {
        val json = sendGet("https://www.amonteiro.fr/api/randomuser")
        return gson.fromJson(json, UserBean::class.java)
    }


    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}\n${it.body.string()}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}

data class WeatherAroundBean(var list: List<WeatherBean>)
data class WeatherBean(var id: String, var name: String, var main: TempBean, var wind: WindBean, var weather: List<DescriptionBean>)
data class TempBean(var temp: Double)
data class WindBean(var speed: Double)
data class DescriptionBean(var description: String, var icon: String)

/* -------------------------------- */
// USER
/* -------------------------------- */
data class UserBean(var name: String, var age: Int, var coord: CoordBean?)
data class CoordBean(var phone: String?, var mail: String?)
