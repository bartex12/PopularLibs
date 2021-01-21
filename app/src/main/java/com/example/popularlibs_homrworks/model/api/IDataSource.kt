package com.example.popularlibs_homrworks.model.api

import com.example.popularlibs_homrworks.model.GithubUser
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSource {
   @GET("/users")
   fun getUsers():Single<List<GithubUser>>

//    //Чтобы загрузить данные одного пользователя, нам потребуется еще один метод
//    @GET("users/{login}")
//    fun loadUser(@Path("login") login:String):Single<GithubUser>
}