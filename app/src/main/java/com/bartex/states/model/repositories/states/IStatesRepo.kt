package com.bartex.states.model.repositories.states

import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single

interface IStatesRepo {
    fun getStates(): Single<List<State>>
}