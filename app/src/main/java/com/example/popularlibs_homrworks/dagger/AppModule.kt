package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.App
import dagger.Module
import dagger.Provides

@Module
class AppModule(val app: App) {
    @Provides
    fun app(): App {
        return app
    }
}