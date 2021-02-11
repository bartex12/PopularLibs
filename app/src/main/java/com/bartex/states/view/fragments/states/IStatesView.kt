package com.bartex.states.view.fragments.states

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IStatesView: MvpView {
    fun init()
    fun updateList()
}