package com.example.a24_08_avl.exo

fun main() {
    exo1()
}

fun exo1() {
//    lower : Prend un texte et l’affiche en minuscule (.lowercase())
    var lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    var lower2 = { it: String -> println(it.lowercase()) }
    var lower3: (String) -> Unit = { it -> println(it.lowercase()) }
    var lower4: (String) -> Unit = { println(it.lowercase()) }
    lower("Salut")

//    hour : prenant un nombre de minutes et retournant le nombre d’heures équivalentes
    val hour: (Int) -> Int = { it / 60 }

//    max : prenant 2 entiers et retournant le plus grand (Math.max())
    val max = { a: Int, b: Int -> Math.max(a, b) }


//    reverse : retourne le texte à l’envers toto -> otot (.reversed()
    var reverse: ((String?) -> String?)? = { it?.reversed() }
    reverse = null

    val res = reverse?.invoke(null)
    println(res)
}