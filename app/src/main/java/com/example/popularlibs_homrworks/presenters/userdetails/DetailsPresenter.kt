package com.example.popularlibs_homrworks.presenters.userdetails

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.view.fragments.details.DetailsView
import com.example.popularlibs_homrworks.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class DetailsPresenter(val router: Router, val user: GithubUser):
    MvpPresenter<DetailsView>()  {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.setUserForks()
    }

    fun backPressed():Boolean {
        router.backTo(Screens.UserScreen(user))
        Log.d(TAG, "UserPresenter backPressed ")
        return true
    }
}