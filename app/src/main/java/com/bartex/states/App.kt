package com.bartex.states

import android.app.Application
import com.bartex.states.dagger.AppComponent
import com.bartex.states.dagger.AppModule
import com.bartex.states.dagger.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var appComponent: AppComponent


    override fun onCreate() {
        super.onCreate()
        instance = this //здесь определяем свойство instance - контекст приложения

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}