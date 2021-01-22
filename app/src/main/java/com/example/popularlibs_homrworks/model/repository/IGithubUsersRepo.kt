package com.example.popularlibs_homrworks.model.repository

import com.example.popularlibs_homrworks.model.entity.GithubUser
import io.reactivex.rxjava3.core.Single

//наведём небольшой архитектурный порядок, спрятав репозиторий за интерфейс:
interface IGithubUsersRepo {
    fun getUsers(): Single<List<GithubUser>>
}