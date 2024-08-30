package com.example.a24_08_avl.car

import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.SearchTemplate
import androidx.car.app.model.Template
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.a24_08_avl.model.WeatherAPI
import com.example.a24_08_avl.model.WeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyScreen(carContext: CarContext) : Screen(carContext), DefaultLifecycleObserver {

    var items: List<WeatherBean> = ArrayList<WeatherBean>()
    var runInProgress = false

    init {
        lifecycle.addObserver(this)
    }

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

    }


    //Utilisation de ListTemplate
    override fun onGetTemplate(): Template {

        val itemListBuilder = ItemList.Builder()

        // Remplir la liste avec des Row
        items.forEach { item ->
            val row = Row.Builder()
                .setTitle(item.name)
                .addText("Il fait ${item.main.temp}° à ${item.name} (id=${item.id}) avec un vent de ${item.wind.speed} m/s)")
                .setOnClickListener {
                    println("Clic on $item")
                    //Redirection vers une Autre Activity en dehors de la library
//                    val intent = Intent(carContext, MainActivity::class.java)
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    carContext.startActivity(intent)
                }
                .build()

            itemListBuilder.addItem(row)
        }

        //val builder = ListTemplate.Builder()

        val builder = SearchTemplate.Builder(object : SearchTemplate.SearchCallback {
            //onSearchTextChanged possible pour une détéction à chaque caractère
            override fun onSearchSubmitted(searchText: String) {
                //Callback appelé lors de la validation du texte
                super.onSearchSubmitted(searchText)
                runInProgress = true
                invalidate()
                lifecycleScope.launch(Dispatchers.Default) {
                    items = WeatherAPI.loadWeathers("Toulouse")
                    runInProgress = false
                    invalidate()
                }
            }
        })

        builder.setLoading(runInProgress)
        if(!runInProgress) {
            builder.setItemList(itemListBuilder.build())
        }

        // Construire et retourner le ListTemplate
        return builder
            //.setTitle("Liste d'Éléments")
            //Action.BACK : Retour
            //Action.APP_ICON : Affichage de l'icône
            .setHeaderAction(Action.BACK)
            .build()
    }
}