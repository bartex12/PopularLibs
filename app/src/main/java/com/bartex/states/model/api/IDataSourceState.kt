package com.bartex.states.model.api

import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IDataSourceState {

    @GET("all")
    fun getStates(): Single<List<State>>
}