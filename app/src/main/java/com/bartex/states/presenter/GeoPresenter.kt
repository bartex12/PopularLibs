package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.geo.IGeoRepo
import com.bartex.states.view.fragments.geo.IGeoView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class GeoPresenter(val state: State?): MvpPresenter<IGeoView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var geoRepo: IGeoRepo

    companion object{
        const val TAG = "33333"
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(TAG, "GeoPresenter onFirstViewAttach ")
        geoRepo.sendGeoIntent(state)
    }

    fun backPressed():Boolean {
        Log.d(TAG, "GeoPresenter backPressed ")
        router.exit()
        return true
    }
}