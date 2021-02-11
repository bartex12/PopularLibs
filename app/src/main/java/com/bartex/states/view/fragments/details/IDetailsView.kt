package com.bartex.states.view.fragments.details

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IDetailsView: MvpView {
    fun setStateName()
    fun setStateRegion()
    fun setStateFlag()
    fun setStateArea()
    fun setStatePopulation()
    fun setStateCapital()
    fun setBtnCapitalEnabled()
}