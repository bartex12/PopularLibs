package com.bartex.states.model.repositories.states.cash

import com.bartex.states.model.entity.state.State
import io.reactivex.rxjava3.core.Single

interface IRoomStateCash {
    fun doStatesCash(listStates:List<State>):Single<List<State>>
    fun getStatesFromCash():Single<List<State>>
}