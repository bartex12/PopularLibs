package com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers

import com.example.popularlibs_homrworks.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

//интерфейс для кэширования списка пользователей из сети в базу данных
interface IRoomGithubUsersCache {
    fun doUsersCache(githubUserList:List<GithubUser>): Single<List<GithubUser>>
    fun getUsersFromCash(): Single<List<GithubUser>>
}