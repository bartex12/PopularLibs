package com.bartex.states.dagger

import com.bartex.states.model.api.IDataSourceState
import com.bartex.states.model.api.IDataSourceWeather
import com.bartex.states.model.network.INetworkStatus
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.model.repositories.states.StatesRepo
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.model.repositories.weather.IWeatherRepo
import com.bartex.states.model.repositories.weather.WeatherRepo
import com.bartex.states.model.repositories.weather.cash.IRoomWeatherCash
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides
    @Singleton
    fun statesRepo(api: IDataSourceState, networkStatus: INetworkStatus,
                   roomCash: IRoomStateCash):IStatesRepo =
        StatesRepo(api,networkStatus, roomCash)

    @Provides
    @Singleton
    fun weatherRepo(api: IDataSourceWeather, networkStatus: INetworkStatus,
                    roomWeatherCash: IRoomWeatherCash): IWeatherRepo =
        WeatherRepo(api,networkStatus, roomWeatherCash)

}