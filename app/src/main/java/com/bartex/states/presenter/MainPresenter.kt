package com.bartex.states.presenter

import com.bartex.states.Screens
import com.bartex.states.view.main.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.StatesScreen())
    }

    fun backClicked() {
        router.exit()
    }
}