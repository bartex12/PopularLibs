package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.presenters.main.MainPresenter
import com.example.popularlibs_homrworks.presenters.users.UsersPresenter
import com.example.popularlibs_homrworks.view.fragments.details.DetailsFragment
import com.example.popularlibs_homrworks.view.fragments.user.UserFragment
import com.example.popularlibs_homrworks.view.fragments.users.UsersFragment
import com.example.popularlibs_homrworks.view.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        CiceroneModule::class,
        CacheModule::class,
        ApiModule::class,
        RepoModule::class
    ]
)

interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)

    //todo убрать
    fun inject(usersFragment: UsersFragment)
    fun inject(userFragment: UserFragment)
    fun inject(detailsFragment: DetailsFragment)

}