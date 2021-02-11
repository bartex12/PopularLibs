package com.bartex.states.presenter.details

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.view.fragments.details.IDetailsView
import com.bartex.states.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class DetailsPresenter(val router: Router):
    MvpPresenter<IDetailsView>()  {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setStateName()
        viewState.setStateRegion()
        viewState.setStateArea()
        viewState.setStatePopulation()
        viewState.setStateCapital()
        viewState.setStateFlag()

    }

    fun btnEnabled(){
        viewState.setBtnCapitalEnabled()
    }

    fun btnCapitalClick(state: State){
        Log.d(TAG, "DetailsPresenter btnCapitalClick state = $state ")
        router.navigateTo(Screens.WeatherScreen(state))
    }

    fun backPressed():Boolean {
        Log.d(TAG, "DetailsPresenter backPressed ")
        router.replaceScreen(Screens.StatesScreen())
        return true
    }
}