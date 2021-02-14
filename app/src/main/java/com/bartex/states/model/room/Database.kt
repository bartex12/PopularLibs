package com.bartex.states.model.room

import androidx.room.RoomDatabase
import com.bartex.states.model.room.dao.StateDao
import com.bartex.states.model.room.dao.WeatherDao
import com.bartex.states.model.room.tables.RoomState
import com.bartex.states.model.room.tables.RoomWeather

/*
* Перечисляем сущности в аннотации, наследуемся от RoomDatabase и перечисляем DAO в виде
*  абстрактных полей.
*/
@androidx.room.Database(entities = [RoomState::class, RoomWeather::class], version = 1 )
abstract class Database: RoomDatabase() {
    abstract val stateDao: StateDao
    abstract val weatherDao: WeatherDao

    companion object{
        const val DB_NAME = "database.db"
    }
}