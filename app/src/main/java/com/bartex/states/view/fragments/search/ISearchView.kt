package com.bartex.states.view.fragments.search

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface ISearchView : MvpView {
    fun init()
    fun updateList()
}