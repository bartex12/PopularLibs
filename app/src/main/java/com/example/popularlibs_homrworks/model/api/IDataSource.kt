package com.example.popularlibs_homrworks.model.api

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface IDataSource {
   @GET("users")
   fun getUsers():Single<List<GithubUser>>

    //Чтобы загрузить данные одного пользователя, нам потребуется еще один метод
    @GET
    fun getUserRepos(@Url url:String):Single<List<GithubUserRepos>>
}