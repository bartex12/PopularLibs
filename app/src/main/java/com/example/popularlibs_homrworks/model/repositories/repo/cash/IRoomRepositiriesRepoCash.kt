package com.example.popularlibs_homrworks.model.repositories.repo.cash

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.room.Database
import io.reactivex.rxjava3.core.Single

//интерфейс для кэширования списка репозиториев пользователя user из сети в базу данных
interface IRoomRepositiriesRepoCash {
    fun doUserReposCache(user: GithubUser, repos:List<GithubUserRepos>, db: Database): Single<List<GithubUserRepos>>
}