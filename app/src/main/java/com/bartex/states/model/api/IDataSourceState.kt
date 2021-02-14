package com.bartex.states.model.api

import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataSourceState {

    @GET("all")
    fun getStates(): Single<List<State>>

    @GET("name/{name}")
    fun searchStates( @Path("name") name: String): Single<List<State>>
}