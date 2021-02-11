package com.bartex.states.model.repositories.weather

import com.bartex.states.model.api.weather.IDataSourceWeather
import com.bartex.states.model.entity.weather.WeatherInCapital
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherRepo(val api: IDataSourceWeather):
    IWeatherRepo {

    override fun getWeatherInCapital(
        city: String?,keyApi: String?,units: String?, lang:String?): Single<WeatherInCapital> =
        api.loadWeatherInCapitalRu(city, keyApi, units, lang).subscribeOn(Schedulers.io())

}