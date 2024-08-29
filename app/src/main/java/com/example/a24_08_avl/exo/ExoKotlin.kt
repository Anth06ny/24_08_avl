package com.example.a24_08_avl.exo

import com.example.a24_08_avl.model.CarBean



fun main() {

//    var data : MyLiveData = MyLiveData( "Coucou")
//    data.action = {
//        println(it)
//    }
//
//    data.value = "Blabla"

}

class MyLiveData(value : String) {
    var value : String = value
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action : (String)->Unit = {}
}





fun scanNumber(question: String) = scanText(question).toInt() ?: 0

fun scanText(question: String): String {

    print(question)
    return readlnOrNull() ?: "-"
}

fun boulangerie(nbCroissant: Int = 0, nbSandwitch: Int = 0, nbBaguette: Int = 0): Double =
    nbCroissant * PRICE_CROISSANT + nbSandwitch * PRICE_SANDWITCH + nbBaguette * PRICE_BAGUETTE


fun pair(c: Int) = c % 2 == 0
fun myPrint(text: String) = println("#$text#")

fun min(a: Int, b: Int, c: Int): Int =
    if (a <= b && a <= c) a else if (b <= a && b <= c) b else c









































