package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.App
import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.network.AndroidNetworkStatus
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApiModule {

    @Singleton
    @Provides
   fun  json(): Gson = GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .excludeFieldsWithoutExposeAnnotation()
                .create()

    @Named("baseUrl")
    @Provides
    fun baseUrl():String = "https://api.github.com/"

    @Singleton  //так как Singleton - нужно передавать параметры в конструкторе!
    @Provides
    fun api(@Named("baseUrl")baseUrl:String, json: Gson) : IDataSource =
        Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(json))
                .build()
                .create(IDataSource::class.java)

    @Singleton
    @Provides
    fun networkStatus(app: App) = AndroidNetworkStatus(app)
}