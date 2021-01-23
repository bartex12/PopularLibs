package com.example.popularlibs_homrworks.presenters.main

import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.main.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


class MainPresenter(val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}