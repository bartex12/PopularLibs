package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.UsersView
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.view.UserItemView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

const val TAG ="33333"

class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router):
    MvpPresenter<UsersView>() {

    val usersListPresenter =  UsersListPresenter()

    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            //TODO: переход на экран пользователя
           val login =  usersListPresenter.users[itemView.pos].login
            Log.d(TAG, "UsersPresenter itemClickListener login =$login")
        }
    }

    fun loadData() {
        val users =  usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}