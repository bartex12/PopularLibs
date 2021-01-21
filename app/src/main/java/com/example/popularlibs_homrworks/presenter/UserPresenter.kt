package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.view.fragments.UserView
import com.example.popularlibs_homrworks.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


class UserPresenter(val router: Router, val user: GithubUser):
    MvpPresenter<UserView>()  {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        user.login?. let{viewState.setLogin(it)}
    }

    fun backPressed():Boolean {
        router.exit()
        Log.d(TAG, "UserPresenter backPressed ")
        return true
    }
}
