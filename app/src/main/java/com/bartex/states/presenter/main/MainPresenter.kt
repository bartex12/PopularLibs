package com.bartex.states.presenter.main

import com.bartex.states.Screens
import com.bartex.states.view.main.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.StatesScreen())
    }

    fun backClicked() {
        router.exit()
    }
}