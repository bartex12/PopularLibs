package com.bartex.states.model.repositories.weather

import com.bartex.states.model.entity.weather.WeatherInCapital
import io.reactivex.rxjava3.core.Single

interface IWeatherRepo {
    fun getWeatherInCapital(
        capital: String?,keyApi: String?,units: String?, lang:String?): Single<WeatherInCapital>
}