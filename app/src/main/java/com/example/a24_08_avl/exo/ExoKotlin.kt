package com.example.a24_08_avl.exo

fun main() {

//    var v1 = "Toto"
//    println(v1.uppercase())
//
//    var v2 : String? = "Toto"
//    println(v2?.uppercase())
//
//    var v3 : String? = null
//    println(v3?.uppercase())
//    var v4 = min(5, 6, 7)
//    println(v4)

    println(boulangerie(nbBaguette = 3))
    println(scanNumber("Une phrase : "))
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









































