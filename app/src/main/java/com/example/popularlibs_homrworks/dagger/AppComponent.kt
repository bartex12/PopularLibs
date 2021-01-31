package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.model.glide.GlideImageLoader
import com.example.popularlibs_homrworks.presenters.details.DetailsPresenter
import com.example.popularlibs_homrworks.presenters.main.MainPresenter
import com.example.popularlibs_homrworks.presenters.user.UserRepoPresenter
import com.example.popularlibs_homrworks.presenters.users.UsersPresenter
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
        RepoModule::class,
        AvatarModule::class
    ]
)

interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(mainPresenter: MainPresenter)
    fun inject(usersPresenter: UsersPresenter)
    fun inject(userRepoPresenter: UserRepoPresenter)
    fun inject(detailsPresenter: DetailsPresenter)

    fun inject(glideImageLoader: GlideImageLoader)


}