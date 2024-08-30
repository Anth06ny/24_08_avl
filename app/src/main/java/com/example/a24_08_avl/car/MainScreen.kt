package com.example.a24_08_avl.car

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Intent
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.GridTemplate
import androidx.car.app.model.ItemList
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.SearchTemplate
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide.init
import com.example.a24_08_avl.MainActivity
import com.example.a24_08_avl.R
import com.example.a24_08_avl.model.WeatherAPI
import com.example.a24_08_avl.model.WeatherBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreen(carContext: CarContext) : Screen(carContext) {

    var carPropertyManager = Car.createCar(carContext)?.getCarManager(Car.PROPERTY_SERVICE) as? CarPropertyManager

    var speed = ""

    init {
        // Register listener for vehicle speed property
        carPropertyManager?.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    //On récupère la nouvelle valeur
                    speed = p0?.value?.toString() ?: "-"
                    invalidate()
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    println("p0=$p0 p1=$p1")
                }
            },
            VehiclePropertyIds.PERF_VEHICLE_SPEED,
            CarPropertyManager.SENSOR_RATE_NORMAL)
    }


    //Utilisation de ListTemplate
    override fun onGetTemplate(): Template {
        val gridItemList = ItemList.Builder()
            .addItem(
                GridItem.Builder()
                    .setTitle("Weather")
                    .setImage(CarIcon.Builder(IconCompat.createWithResource(carContext, R.mipmap.ic_launcher)).build() )
                    .setOnClickListener {
                        screenManager.push(MyScreen(carContext))
                    }
                    .build()
            )
            .addItem(
                GridItem.Builder()
                    .setTitle("Mobile")
                    .setImage(CarIcon.Builder(IconCompat.createWithResource(carContext, R.mipmap.ic_launcher)).build() )
                    .setOnClickListener {
                        //Redirection vers une Autre Activity en dehors de la library
                        val intent = Intent(carContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        carContext.startActivity(intent)
                    }
                    .build()
            )
            .addItem(
                GridItem.Builder()
                    .setTitle("Speed : $speed")
                    .setImage(CarIcon.Builder(IconCompat.createWithResource(carContext, R.mipmap.ic_launcher)).build() )
                    .setOnClickListener {
                        //Redirection vers une Autre Activity en dehors de la library
                        val intent = Intent(carContext, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        carContext.startActivity(intent)
                    }
                    .build()
            )
            .build()

// Création du template GridTemplate
        return GridTemplate.Builder()
            .setTitle("Accueil")
            .setHeaderAction(Action.APP_ICON)
            .setSingleList(gridItemList)
            .build()

    }
}