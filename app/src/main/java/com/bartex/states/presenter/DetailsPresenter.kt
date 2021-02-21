package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.model.utils.IStateUtils
import com.bartex.states.view.fragments.details.IDetailsView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsPresenter(val state: State?):MvpPresenter<IDetailsView>()  {

    @Inject
    lateinit var  roomCash: IRoomStateCash

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var stateUtils: IStateUtils

    companion object{
        const val TAG = "33333"
    }

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

    fun toHome(){
        Log.d(TAG, "DetailsPresenter toHome  ")
        router.newRootScreen(Screens.StatesScreen())
    }

    fun sendGeoIntent(state:State){
        viewState.sendGeoIntent(stateUtils.getStatezoom(state))
    }

    fun showFavoritesFragment(){
        router.navigateTo(Screens.FavoriteScreen())
    }

    fun isFavorite(state:State){
      roomCash.isFavorite(state)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
          .subscribe({
                viewState.setVisibility(it)
          }, {
                Log.d(TAG, "DetailsPresenter isFavorite error = ${it.message} ")
          })
    }

    fun addToFavorite(state:State){
        roomCash.addToFavorite(state)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (   {
                viewState.showAddFavoriteToast()
            },{
                Log.d(TAG, "DetailsPresenter addToFavorite error = ${it.message} ")
            })


        Log.d(TAG, "DetailsPresenter addToFavorite ")
    }
    fun removeFavorite(state:State){
        //todo
    }

    fun backPressed():Boolean {
        Log.d(TAG, "DetailsPresenter backPressed ")
        router.exit()
        return true
    }
}