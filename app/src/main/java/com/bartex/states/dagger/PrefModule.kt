package com.bartex.states.dagger

import com.bartex.states.App
import com.bartex.states.model.repositories.prefs.IPreferenceHelper
import com.bartex.states.model.repositories.prefs.PreferenceHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PrefModule {

    @Provides
    @Singleton
    fun helperRepo(app: App): IPreferenceHelper =
        PreferenceHelper(app)
}