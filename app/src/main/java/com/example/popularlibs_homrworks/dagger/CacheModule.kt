package com.example.popularlibs_homrworks.dagger

import androidx.room.Room
import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos.IRoomRepositiriesRepoCash
import com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos.RoomRepositoriesRepoCash
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers.IRoomGithubUsersCache
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers.RoomGithubUsersCache
import com.example.popularlibs_homrworks.model.room.Database
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Singleton
    @Provides
    fun usersCash(database: Database): IRoomGithubUsersCache {
        return RoomGithubUsersCache(database)
    }

    @Singleton
    @Provides
    fun userCash(database: Database): IRoomRepositiriesRepoCash {
        return RoomRepositoriesRepoCash(database)
    }
}