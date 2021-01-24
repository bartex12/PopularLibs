package com.example.popularlibs_homrworks.model.room.database

import androidx.room.*
import com.example.popularlibs_homrworks.model.room.tablesroom.RoomGithubRepository

/*
*  стандартные CRUD разных вариаций для создания, чтения, обновления и удаления данных, а также
* возможность поискать пользователя по логину. С помощью встроенного шаблонизатора содержимое
*  аргумента login функции findByLogin подставится вместо :login в запрос. В функциях insert
* с помощью аргумента аннотации onConflict мы указываем, что при возникновении конфликта
* по первичному ключу необходимо заменить старое значение новым*/
@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg users: RoomGithubRepository)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<RoomGithubRepository>)

    @Update
    fun update(user: RoomGithubRepository)

    @Update
    fun update(vararg users: RoomGithubRepository)

    @Update
    fun update(users: List<RoomGithubRepository>)

    @Delete
    fun delete(user: RoomGithubRepository)

    @Delete
    fun delete(vararg users: RoomGithubRepository)

    @Delete
    fun delete(users: List<RoomGithubRepository>)

    @Query("SELECT * FROM RoomGithubRepository")
    fun getAll(): List<RoomGithubRepository>

    @Query("SELECT * FROM RoomGithubRepository WHERE userId = :userId")
    fun findForUser(userId: String): List<RoomGithubRepository>
}