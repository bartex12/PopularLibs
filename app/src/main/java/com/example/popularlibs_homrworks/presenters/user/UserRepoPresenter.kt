package com.example.popularlibs_homrworks.presenters.user

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.repositories.repo.IGithubRepositoriesRepo
import com.example.popularlibs_homrworks.view.adapters.user.UserRepoItemView
import com.example.popularlibs_homrworks.view.fragments.user.UserView
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router


class UserRepoPresenter(val mainThreadScheduler: Scheduler,
                        val usersRepo: IGithubRepositoriesRepo, val router: Router, val user: GithubUser)
    :MvpPresenter<UserView>()  {

    val userListPresenter = UserListPresenter()
    //вложенный класс для работы с адаптером
    class UserListPresenter :IUserRepoListPresenter {

        val users = mutableListOf<GithubUserRepos>()

        override var itemClickListener: ((UserRepoItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserRepoItemView) {
            val user = users[view.pos]
            user.id?. let{view.setId(it)}
            user.name?. let{view.setName(it) }
            user.forks?. let{view.setForks(it) }
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.init()
        loadRepos()

        //переход на экран детализации по щелчку на строке списка
        userListPresenter.itemClickListener = {itemView ->
            Log.d(TAG, "UserRepoPresenter itemClickListener itemView.pos = ${itemView.pos} ")
            val userForks = userListPresenter.users[itemView.pos]
            router.navigateTo(Screens.UserForksScreen(userForks, user))
        }
    }

    private fun loadRepos() {
            usersRepo.getUserRepo(user)
                .observeOn(mainThreadScheduler)
                .subscribe(
                    {
                        userListPresenter.users.clear()
                        userListPresenter.users.addAll(it)
                        viewState.updateList()
                        Log.d(TAG, "UserRepoPresenter onFirstViewAttach list.size = ${it.size} ")
                    },
                    { Log.d(TAG, "error ") })
    }

    fun backPressed():Boolean {
        Log.d(TAG, "UserRepoPresenter backPressed ")
        router.backTo(Screens.UsersScreen())
        return true
    }
}
