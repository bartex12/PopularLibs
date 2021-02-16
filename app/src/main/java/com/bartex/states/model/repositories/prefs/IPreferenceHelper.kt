package com.bartex.states.model.repositories.prefs

interface IPreferenceHelper {
    fun savePosition(position:Int)
    fun getPosition(): Int
    fun savePositionSearch(position:Int)
    fun getPositionSearch(): Int
    fun getSortCase():Int
    fun isSorted():Boolean
}