package com.bartex.states

import com.bartex.states.model.entity.state.State
import com.bartex.states.view.fragments.details.DetailsFragment
import com.bartex.states.view.fragments.states.StatesFragment
import com.bartex.states.view.fragments.weather.WeatherFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {
    class StatesScreen() : SupportAppScreen() {
        override fun getFragment() = StatesFragment.newInstance()
    }
    class DetailsScreen(val state: State) : SupportAppScreen() {
        override fun getFragment() = DetailsFragment.newInstance(state)
    }

    class WeatherScreen(val state: State) : SupportAppScreen() {
        override fun getFragment() = WeatherFragment.newInstance(state)
    }
}