package com.example.popularlibs_homrworks

import android.app.Application
import com.example.popularlibs_homrworks.dagger.AppComponent
import com.example.popularlibs_homrworks.dagger.AppModule
import com.example.popularlibs_homrworks.dagger.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var instance: App  //отлож иниц свойства со статич доступом извне
    }

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this   //здесь определяем свойство instance - контекст приложения

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()


    }


}