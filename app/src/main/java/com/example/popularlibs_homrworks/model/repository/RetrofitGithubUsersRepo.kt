package com.example.popularlibs_homrworks.model.repository

import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.api.ApiHolder
import com.example.popularlibs_homrworks.model.api.IDataSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubUsersRepo(val api:IDataSource):IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        api.getUsers().subscribeOn(Schedulers.io())

}