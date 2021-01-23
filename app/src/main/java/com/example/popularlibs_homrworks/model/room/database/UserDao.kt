package com.example.popularlibs_homrworks.model.room.database

import androidx.room.*
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubUser

/*
*  стандартные CRUD разных вариаций для создания, чтения, обновления и удаления данных, а также
* возможность поискать пользователя по логину. С помощью встроенного шаблонизатора содержимое
*  аргумента login функции findByLogin подставится вместо :login в запрос. В функциях insert
* с помощью аргумента аннотации onConflict мы указываем, что при возникновении конфликта
* по первичному ключу необходимо заменить старое значение новым*/
@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user: RoomGithubUser)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users:List<RoomGithubUser>)

    @Delete
    fun delete(user: RoomGithubUser)

    @Delete
    fun delete(vararg user: RoomGithubUser)

    @Delete
    fun delete(users:List<RoomGithubUser>)

    @Update
    fun update(user: RoomGithubUser)
    @Update
    fun update(vararg user: RoomGithubUser)
    @Update
    fun update(users:List<RoomGithubUser>)

    @Query("SELECT * FROM RoomGithubUser")
    fun getAll():List<RoomGithubUser>

    @Query("SELECT * FROM RoomGithubUser WHERE login = :login LIMIT 1")
    fun findByLogin(login:String): RoomGithubUser?
}