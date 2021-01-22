package com.example.popularlibs_homrworks

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.view.fragments.UserFragment
import com.example.popularlibs_homrworks.view.fragments.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        // через статический метод newInstance()
        override fun getFragment() = UsersFragment.newInstance()
    }
    class UserScreen(val user: GithubUser) : SupportAppScreen() {
        // через конструктор - для разнообразия
        override fun getFragment() = UserFragment(user)
    }
}