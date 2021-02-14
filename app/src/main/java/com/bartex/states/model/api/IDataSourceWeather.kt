package com.bartex.states.model.api

import com.bartex.states.model.entity.weather.WeatherInCapital
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IDataSourceWeather {

    @GET("data/2.5/weather")
    fun loadWeatherInCapitalRu(
        @Query("q") capital: String?,
        @Query("appid") keyApi: String?,
        @Query("units") units: String?,
        @Query("lang") lang: String?
    ): Single<WeatherInCapital>


    @GET("data/2.5/weather")
    fun loadWeatherInCapitalEng(
        @Query("q") capital: String?,
        @Query("appid") keyApi: String?,
        @Query("units") units: String?
    ): Single<WeatherInCapital>
}