package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.utils.IStateUtils
import com.bartex.states.view.fragments.details.IDetailsView
import com.bartex.states.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsPresenter(val state: State?):MvpPresenter<IDetailsView>()  {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var stateUtils: IStateUtils

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setStateName()
        viewState.setStateRegion()
        viewState.setStateFlag()
        viewState.setStateArea(stateUtils.getStateArea(state))
        viewState.setStatePopulation(stateUtils.getStatePopulation(state))
        viewState.setStateCapital(stateUtils.getStateCapital(state))
    }

    fun btnCapitalClick(state: State){
        Log.d(TAG, "DetailsPresenter btnCapitalClick state = $state ")
        router.navigateTo(Screens.WeatherScreen(state))
    }

    fun btnGeoClick(state:State){
        Log.d(TAG, "DetailsPresenter btnGeoClick state = $state ")
        router.navigateTo(Screens.GeoScreen(state))
    }

    fun sendGeoIntent(state:State){
        viewState.sendGeoIntent(stateUtils.getStatezoom(state))
    }

    fun backPressed():Boolean {
        Log.d(TAG, "DetailsPresenter backPressed ")
        router.exit()
        return true
    }
}