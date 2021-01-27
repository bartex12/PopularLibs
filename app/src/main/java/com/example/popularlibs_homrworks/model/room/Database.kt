package com.example.popularlibs_homrworks.model.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.popularlibs_homrworks.model.room.dao.CashedImageDao
import com.example.popularlibs_homrworks.model.room.dao.RepositoryDao
import com.example.popularlibs_homrworks.model.room.dao.UserDao
import com.example.popularlibs_homrworks.model.room.tables.CashedImage
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubRepository
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubUser

/*
* Перечисляем сущности в аннотации, наследуемся от RoomDatabase и перечисляем DAO в виде
*  абстрактных полей. Весь остальной код тут для временного Singletone,
* пока мы не внедрим в проект DI.
*/
@androidx.room.Database(entities = [
RoomGithubUser::class, RoomGithubRepository::class, CashedImage::class], version = 1 )
abstract class Database:RoomDatabase() {
    abstract val userDao: UserDao
    abstract val repositoryDao: RepositoryDao
    abstract val cashedImage: CashedImageDao

    companion object{
        private const val DB_NAME = "database.db"
        private var instance: Database? = null
        fun getInstance() = instance
            ?: throw RuntimeException("Database has not been created." +
                " Please call create(context)")
        fun create(context: Context?) {
            if (instance == null) {
                instance = Room.databaseBuilder(context!!, Database::class.java,
                    DB_NAME
                )
                    .build()
            }
        }
    }
}