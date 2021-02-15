package com.bartex.states.dagger

import com.bartex.states.presenter.*
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
        RepoModule::class,
        PrefModule::class,
        HelpModule::class,
        GeoModule::class
    ]
)

interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(mainPresenter: MainPresenter)
    fun inject(statesPresenter: StatesPresenter)
    fun inject(detailsPresenter: DetailsPresenter)
    fun inject(weatherPresenter: WeatherPresenter)
    fun inject(searchPresenter: SearchPresenter)
    fun inject(helpPresenter: HelpPresenter)
    fun inject(geoPresenter: GeoPresenter)
    //fun inject(glideToVectorYouLoader: GlideToVectorYouLoader)

}