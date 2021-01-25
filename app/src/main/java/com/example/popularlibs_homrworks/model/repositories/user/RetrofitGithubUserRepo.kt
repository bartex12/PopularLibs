package com.example.popularlibs_homrworks.model.repositories.user

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUserRepo(val api: IDataSource):
    IGithubUserRepo {

    override fun getUserRepo(url:String): Single<List<GithubUserRepos>> =
        api.getUserRepos(url).subscribeOn(Schedulers.io())
}