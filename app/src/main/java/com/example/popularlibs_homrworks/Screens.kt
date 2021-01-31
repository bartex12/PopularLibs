package com.example.popularlibs_homrworks

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.view.fragments.details.DetailsFragment
import com.example.popularlibs_homrworks.view.fragments.user.UserFragment
import com.example.popularlibs_homrworks.view.fragments.users.UsersFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class UsersScreen() : SupportAppScreen() {
        // через статический метод newInstance()
        override fun getFragment() = UsersFragment.newInstance()
    }
    class UserScreen(val user: GithubUser) : SupportAppScreen() {
        //через статический метод newInstance()
        override fun getFragment() = UserFragment.newInstance( user)
    }
    class UserForksScreen(val userRepos: GithubUserRepos, val user: GithubUser) : SupportAppScreen() {
        // через статический метод newInstance()
        override fun getFragment() = DetailsFragment.newInstance(userRepos, user)
    }
}