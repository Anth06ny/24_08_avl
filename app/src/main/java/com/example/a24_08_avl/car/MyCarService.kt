package com.example.a24_08_avl.car

import androidx.car.app.CarAppService
import androidx.car.app.Session
import androidx.car.app.validation.HostValidator

class MyCarService : CarAppService() {

    //Appelé quand l'application démarre et qu'une nouvelle session est nécessaire
    //Point d'entrée de l'application
    override fun onCreateSession(): Session {
        //C'est ici qu'on lance les metrics
        return MySession()
    }

    //Qui peut venir appeler le service
    //Autres applications? L'os? ...
    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR
    }
}