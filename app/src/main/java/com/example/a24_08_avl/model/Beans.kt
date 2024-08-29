package com.example.a24_08_avl.model

import java.util.Random

var name : String? = null

fun main() {

    var somme = 0

    var total = if(somme > 0 ) somme * 1.2 else 0

    if(somme> 0) {
        total=  somme * 1.2
    }
    else {
        total = 0
    }


}

class RandomName {
    private var list = arrayListOf("Toto", "Tata", "Titi")
    private var oldValue = ""

    fun add(name: String?) = if (!name.isNullOrBlank() && name !in list) {
        list.add(name)
    } else false

    fun next() = list.random()

    fun addAll(vararg names:String){
        for(name in names) {
            add(name)
        }
    }

    fun nextDiff(): String {
        var newValue = next()
        while(newValue == oldValue) {
            newValue = next()
        }
        oldValue = newValue
        return newValue
    }

    fun nextDiff2(): String {
        oldValue = list.filter { it != oldValue  }.random()
        return oldValue
    }

    fun nextDiff3()  = list.filter { it != oldValue  }.random().also { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())



}

class ThermometerBean(val min: Int = -20, val max: Int = 50, value: Int = 0) {
    var value = value.coerceIn(min, max)
        set(newValue) {
            field = newValue.coerceIn(min, max)
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerBean(-30, 50, 0)
        fun getFahrenheitThermometer() = ThermometerBean(20, 120, 32)
    }

    override fun toString(): String {
        return "ThermometerBean(min=$min, max=$max, value=$value)"
    }

}

class PrintRandomIntBean(var max: Int) {
    private var random = Random()

    init {
        random.nextInt(max)
        random.nextInt(max)
        random.nextInt(max)
    }

    constructor() : this(100) {
        random.nextInt(max)
    }
}

class HouseBean(var color: String, length: Int, width: Int) {
    var surface: Int =  length * width
}

data class CarBean(var marque: String, var model: String) {
    var color = ""

    fun print() = println("$marque $model $color")

}