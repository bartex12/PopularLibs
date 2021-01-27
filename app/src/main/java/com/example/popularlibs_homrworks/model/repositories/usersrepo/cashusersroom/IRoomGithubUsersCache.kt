package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusersroom

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.room.Database
import io.reactivex.rxjava3.core.Single

//интерфейс для кэширования списка пользователей из сети в базу данных
interface IRoomGithubUsersCache {
    fun doUsersCache(githubUserList:List<GithubUser>, db: Database): Single<List<GithubUser>>
    fun getUsersFromCash( db: Database): Single<List<GithubUser>>
}