package com.example.popularlibs_homrworks.presenters.main

import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.main.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject


class MainPresenter(): MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}