package com.example.popularlibs_homrworks.view

import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.presenter.IUserListPresenter
import moxy.MvpPresenter

class MainPresenter(val usersRepo: GithubUsersRepo): MvpPresenter<MainView>() {

    val usersListPresenter = UsersListPresenter()

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
        }
    }

    fun loadData() {
        val users =  usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }
}