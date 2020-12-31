package com.example.popularlibs_homrworks.model

class CountersModel {
    private val counters = mutableListOf(0,0,0)

    private fun getCurrent(index:Int):Int{
        return counters[index]
    }

    fun getNext(index:Int):Int{
        counters[index]++
        return getCurrent(index)
    }
}