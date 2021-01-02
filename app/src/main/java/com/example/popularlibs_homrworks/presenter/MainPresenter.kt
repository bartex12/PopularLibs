package com.example.popularlibs_homrworks.presenter

import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.MainView
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.UserItemView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class MainPresenter(val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(Screens.UsersScreen())
    }

    fun backClicked() {
        router.exit()
    }
}