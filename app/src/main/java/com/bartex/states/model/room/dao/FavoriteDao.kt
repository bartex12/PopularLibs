package com.bartex.states.model.room.dao

import androidx.room.*
import com.bartex.states.model.room.tables.RoomFavorite
import com.bartex.states.model.room.tables.RoomState

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomFavorite: RoomFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg roomFavorite: RoomFavorite)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomFavorites:List<RoomFavorite>)

    @Delete
    fun delete(roomFavorite: RoomFavorite)

    @Delete
    fun delete(vararg roomFavorite: RoomFavorite)

    @Delete
    fun delete(roomFavorites:List<RoomFavorite>)

    @Update
    fun update(roomFavorite: RoomFavorite)
    @Update
    fun update(vararg roomFavorite: RoomFavorite)
    @Update
    fun update(roomFavorites:List<RoomFavorite>)

    @Query("SELECT * FROM RoomFavorite")
    fun getAll():List<RoomState>


//    @Query("SELECT * FROM RoomFavorite WHERE capital = :capitalName LIMIT 1 ")
//    fun findByCapital(capitalName:String): RoomState
//
//    @Query("SELECT * FROM RoomFavorite WHERE favorite = :isFavorite ")
//    fun findByFavorite(isFavorite:Boolean): List<RoomFavorite>
}