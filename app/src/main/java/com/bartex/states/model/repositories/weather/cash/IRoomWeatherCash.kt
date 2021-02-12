package com.bartex.states.model.repositories.weather.cash

import com.bartex.states.model.entity.state.State
import com.bartex.states.model.entity.weather.WeatherInCapital
import com.bartex.states.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomWeatherCash {
    fun doWeatherCash(weatherInCapital:WeatherInCapital, db:Database): Single<WeatherInCapital>
    fun getWeatherFromCash(capital: String?, db: Database):Single<WeatherInCapital>
}