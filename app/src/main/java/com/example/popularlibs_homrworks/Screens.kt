package com.example.popularlibs_homrworks

import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.view.fragments.UserFragment
import com.example.popularlibs_homrworks.view.fragments.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        override fun getFragment() = UsersFragment.newInstance()
    }
    class UserScreen(val user: GithubUser) : SupportAppScreen() {
        override fun getFragment() = UserFragment(user)
    }
}