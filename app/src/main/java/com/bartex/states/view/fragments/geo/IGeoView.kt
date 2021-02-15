package com.bartex.states.view.fragments.geo

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IGeoView: MvpView {
    //fun sendGeoIntent(geo:String)
}