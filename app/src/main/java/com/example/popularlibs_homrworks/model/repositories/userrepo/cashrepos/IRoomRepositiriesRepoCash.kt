package com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import io.reactivex.rxjava3.core.Single

//интерфейс для кэширования списка репозиториев пользователя user из сети в базу данных
interface IRoomRepositiriesRepoCash {
    fun doUserReposCache(user: GithubUser, repos:List<GithubUserRepos>): Single<List<GithubUserRepos>>
    fun getUsersReposFromCash( user:GithubUser): Single<List<GithubUserRepos>>
}