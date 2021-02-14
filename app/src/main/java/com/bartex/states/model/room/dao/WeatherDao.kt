package com.bartex.states.model.room.dao

import androidx.room.*
import com.bartex.states.model.room.tables.RoomWeather

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomWeather: RoomWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg roomWeather: RoomWeather)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomWeathers:List<RoomWeather>)

    @Delete
    fun delete(roomWeather: RoomWeather)

    @Delete
    fun delete(vararg roomWeather: RoomWeather)

    @Delete
    fun delete(roomWeathers:List<RoomWeather>)

    @Update
    fun update(roomWeather: RoomWeather)
    @Update
    fun update(vararg roomWeather: RoomWeather)
    @Update
    fun update(roomWeathers:List<RoomWeather>)

    @Query("SELECT * FROM RoomWeather")
    fun getAll():List<RoomWeather>

    @Query("SELECT * FROM RoomWeather WHERE capitalName = :capitalName LIMIT 1")
    fun findByName(capitalName:String): RoomWeather?
}