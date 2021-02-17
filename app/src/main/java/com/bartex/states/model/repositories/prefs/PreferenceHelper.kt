package com.bartex.states.model.repositories.prefs

import androidx.preference.PreferenceManager
import android.util.Log
import com.bartex.states.App
import com.bartex.states.view.main.TAG

class PreferenceHelper(val app: App):
    IPreferenceHelper {

    companion object{
        const val FIRST_POSITION = "FIRST_POSITION"
        const val FIRST_POSITION_SEARCH = "FIRST_POSITION_SEARCH"
    }

    override fun savePosition(position:Int) {

        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putInt(FIRST_POSITION, position)
            .apply()
        Log.d(TAG,"PreferenceHelper savePosition position = $position"
        )
    }

    override fun getPosition(): Int {
        val position = PreferenceManager.getDefaultSharedPreferences(app)
            .getInt(FIRST_POSITION, 0)
        Log.d(TAG, "PreferenceHelper getPosition FIRST_POSITION = $position")
        return position
    }

    override fun savePositionSearch(position: Int) {
        PreferenceManager.getDefaultSharedPreferences(app)
            .edit()
            .putInt(FIRST_POSITION_SEARCH, position)
            .apply()
        Log.d(TAG,"PreferenceHelper savePositionSearch position = $position"
        )
    }

    override fun getPositionSearch(): Int {
        val position = PreferenceManager.getDefaultSharedPreferences(app)
            .getInt(FIRST_POSITION_SEARCH, 0)
        Log.d(TAG, "PreferenceHelper getPositionSearch FIRST_POSITION_SEARCH = $position")
        return position
    }

    //получаем файл с настройками сортировки для приложения
    override fun getSortCase(): Int {
        val prefSetting = PreferenceManager.getDefaultSharedPreferences(app)
        val sort = prefSetting.getString("ListSort", "3")!!.toInt()
        return sort
    }

    //получаем файл с настройками для приложения - нужна ли сортировка
    override fun isSorted(): Boolean {
        val prefSetting = PreferenceManager.getDefaultSharedPreferences(app)
        val isSort = prefSetting.getBoolean("cbSort", true)
        return isSort
    }

}