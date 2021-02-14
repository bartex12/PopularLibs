package com.bartex.states.dagger

import com.bartex.states.presenter.DetailsPresenter
import com.bartex.states.presenter.MainPresenter
import com.bartex.states.presenter.StatesPresenter
import com.bartex.states.presenter.WeatherPresenter
import com.bartex.states.view.main.MainActivity
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
    fun inject(statesPresenter: StatesPresenter)
    fun inject(detailsPresenter: DetailsPresenter)
    fun inject(weatherPresenter: WeatherPresenter)

    //fun inject(glideToVectorYouLoader: GlideToVectorYouLoader)

}