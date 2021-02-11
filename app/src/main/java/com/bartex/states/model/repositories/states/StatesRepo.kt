package com.bartex.states.model.repositories.states

import com.bartex.states.model.api.state.IDataSourceState
import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class StatesRepo(val api: IDataSourceState):
    IStatesRepo {

    override fun getStates(): Single<List<State>> =
        api.getStates().subscribeOn(Schedulers.io())

}