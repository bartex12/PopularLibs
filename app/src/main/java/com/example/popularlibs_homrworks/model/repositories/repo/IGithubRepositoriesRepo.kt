package com.example.popularlibs_homrworks.model.repositories.repo

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import io.reactivex.rxjava3.core.Single

interface IGithubRepositoriesRepo {
    fun getUserRepo(user: GithubUser): Single<List<GithubUserRepos>>
}