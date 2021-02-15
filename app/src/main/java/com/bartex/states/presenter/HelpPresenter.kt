package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.model.repositories.help.IHelpRepo
import com.bartex.states.view.fragments.help.IHelpView
import com.bartex.states.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HelpPresenter: MvpPresenter<IHelpView>() {

    @Inject
    lateinit var router:Router

    @Inject
    lateinit var helpRepo:IHelpRepo

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setText(helpRepo.getHelpText())
    }

    fun backPressed():Boolean {
        Log.d(TAG, "DetailsPresenter backPressed ")
        router.exit()
        return true
    }
}