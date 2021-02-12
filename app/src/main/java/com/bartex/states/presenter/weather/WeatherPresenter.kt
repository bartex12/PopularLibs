package com.bartex.states.presenter.weather

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.weather.IWeatherRepo
import com.bartex.states.view.fragments.weather.IWeatherView
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

class WeatherPresenter(val mainThreadScheduler: Scheduler, val weatherRepo: IWeatherRepo,
                       val router: Router, val state: State?): MvpPresenter<IWeatherView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(TAG, "WeatherPresenter  capital = ${state?.capital}")
        loadWeather()
    }

    private fun loadWeather() {
        state?. let {
            weatherRepo.getWeatherInCapital(it.capital,
                "80bb32e4a0db84762bb04ab2bd724646", "metric", "RU")
        }?.observeOn(mainThreadScheduler)
            ?.subscribe(
                {
                    state.name?. let{stateName->viewState.setStateName(stateName)}
                    it.name?. let{capital->viewState.setCapitalName(capital)}
                    it.main?.pressure?. let{pressure-> viewState.setPressure(pressure)}
                    it.main?.humidity?. let{humidity-> viewState.setHumidity(humidity)}
                    it.weather?.get(0)?.description?. let{description->viewState.setDescription(description)}
                    it.main?.temp?. let { temp -> viewState.setTemp(temp)}
                    it.weather?.get(0)?.icon?. let{icon->viewState.setIconDrawble(icon)}
                    Log.d(TAG, "WeatherPresenter onSuccess ${it.name} ${it.main?.temp}")
                },
                {error -> viewState.setErrorMessage()
                    Log.d(TAG, "WeatherPresenter onError ${error.message}")}
            )
    }

    fun backPressed():Boolean {
        Log.d(TAG, "DetailsPresenter backPressed ")
        state?. let{router.backTo(Screens.DetailsScreen(it))}
        return true
    }
}