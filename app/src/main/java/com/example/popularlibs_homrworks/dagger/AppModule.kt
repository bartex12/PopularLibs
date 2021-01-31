package com.example.popularlibs_homrworks.dagger

import com.example.popularlibs_homrworks.App
import dagger.Module
import dagger.Provides
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler

@Module
class AppModule(val app: App) {
    @Provides
    fun app(): App {
        return app
    }

    @Provides
    fun mainThreadScheduler():Scheduler = AndroidSchedulers.mainThread()
}