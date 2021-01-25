package com.example.popularlibs_homrworks.model.repositories.user

import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import io.reactivex.rxjava3.core.Single

interface IGithubUserRepo {
    fun getUserRepo(url:String): Single<List<GithubUserRepos>>
}