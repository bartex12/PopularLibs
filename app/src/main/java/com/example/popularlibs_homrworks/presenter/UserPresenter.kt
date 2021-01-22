package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.model.api.ApiHolder
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.repository.IGithubUserRepo
import com.example.popularlibs_homrworks.model.repository.IGithubUsersRepo
import com.example.popularlibs_homrworks.view.fragments.UserView
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


class UserPresenter(val mainThreadScheduler: Scheduler,
                    val usersRepo: IGithubUserRepo, val router: Router, val user: GithubUser):
    MvpPresenter<UserView>()  {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        user.repos_url?. let{url->
            usersRepo.getUserRepo(url)
                .map {list->list.size }
                .observeOn(mainThreadScheduler)
                .subscribe(
                    {viewState.setUserLogin(it.toString())
                    Log.d(TAG, "UserPresenter onFirstViewAttach list.size = $it ")
                    },
                    {Log.d(TAG, "error ")})

        }
    }

    fun backPressed():Boolean {
        Log.d(TAG, "UserPresenter backPressed ")
        router.navigateTo(Screens.UsersScreen())
        return true
    }
}
