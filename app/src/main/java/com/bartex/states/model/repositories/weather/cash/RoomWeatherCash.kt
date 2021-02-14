package com.bartex.states.model.repositories.weather.cash

import android.util.Log
import com.bartex.states.model.entity.weather.Main
import com.bartex.states.model.entity.weather.Sys
import com.bartex.states.model.entity.weather.Weather
import com.bartex.states.model.entity.weather.WeatherInCapital
import com.bartex.states.model.room.Database
import com.bartex.states.model.room.tables.RoomWeather
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Single

class RoomWeatherCash(val db: Database):IRoomWeatherCash {

    override fun doWeatherCash( weatherInCapital: WeatherInCapital)
            : Single<WeatherInCapital> {

       return Single.fromCallable{
           val roomWeather = RoomWeather(
               weatherInCapital.name?:"",
               weatherInCapital.sys?.country ?:"***",
               weatherInCapital.weather?.get(0)?.description ?:"",
               weatherInCapital.main?.humidity ?:0,
               weatherInCapital.main?.pressure ?:0,
               weatherInCapital.main?.temp ?:0f,
               weatherInCapital.weather?.get(0)?.icon?:""
           )
           Log.d(TAG, "RoomWeatherCash doWeatherCash temp = ${roomWeather.temperature}")
           db.weatherDao.insert(roomWeather) //пишем в базу
         val aa =   weatherInCapital.name?. let{ db.weatherDao.findByName(it)}
           aa?. let{ Log.d(TAG, "RoomWeatherCash doWeatherCash temp из базы = ${aa.temperature}")}
           return@fromCallable weatherInCapital
       }
    }

    override fun getWeatherFromCash(capital: String?): Single<WeatherInCapital> {
       return Single.fromCallable{
           capital?. let{cap->
            val roomWeather =   db.weatherDao.findByName(cap)
               roomWeather?. let{
                   WeatherInCapital(
                       listOf(Weather(it.description, it.iconCod)),
                       Main(it.temperature, it.pressure, it.humidity),
                       Sys(it.country ),
                       it.capitalName)
               }
           }
       }
    }
}

