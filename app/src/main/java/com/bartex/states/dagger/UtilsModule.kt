package com.bartex.states.dagger

import com.bartex.states.model.utils.IStateUtils
import com.bartex.states.model.utils.StateUtils
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {
    @Provides
    @Singleton
    fun utils(): IStateUtils =
        StateUtils()
}