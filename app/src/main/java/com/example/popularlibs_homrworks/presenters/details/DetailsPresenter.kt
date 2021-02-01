package com.example.popularlibs_homrworks.presenters.details

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.view.fragments.details.DetailsView
import com.example.popularlibs_homrworks.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class DetailsPresenter( val user: GithubUser):
    MvpPresenter<DetailsView>()  {

    @Inject
    lateinit var router: Router

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