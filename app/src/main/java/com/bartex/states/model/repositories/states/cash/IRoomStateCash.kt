package com.bartex.states.model.repositories.states.cash

import com.bartex.states.model.entity.state.State
import com.bartex.states.model.room.Database
import io.reactivex.rxjava3.core.Single

interface IRoomStateCash {
    fun doStatesCash(listStates:List<State>, db: Database):Single<List<State>>
    fun getStatesFromCash( db: Database):Single<List<State>>
}