package com.bartex.states.dagger

import com.bartex.states.App
import com.bartex.states.model.repositories.help.HelpRepo
import com.bartex.states.model.repositories.help.IHelpRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class HelpModule {

    @Provides
    @Singleton
    fun helpRepo(app: App): IHelpRepo =
        HelpRepo(app)
}