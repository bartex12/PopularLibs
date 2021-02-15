package com.bartex.states.dagger

import com.bartex.states.App
import com.bartex.states.model.repositories.geo.GeoRepo
import com.bartex.states.model.repositories.geo.IGeoRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class GeoModule {

    @Provides
    @Singleton
    fun geo(app: App):IGeoRepo = GeoRepo(app)
}