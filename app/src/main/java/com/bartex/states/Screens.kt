package com.bartex.states

import android.content.Context
import android.content.Intent
import com.bartex.states.model.entity.state.State
import com.bartex.states.view.fragments.details.DetailsFragment
import com.bartex.states.view.fragments.favorite.FavoriteFragment
import com.bartex.states.view.fragments.geo.GeoFragment
import com.bartex.states.view.fragments.help.HelpFragment
import com.bartex.states.view.fragments.search.SearchFragment
import com.bartex.states.view.fragments.states.StatesFragment
import com.bartex.states.view.fragments.weather.WeatherFragment
import com.bartex.states.view.settings.SettingsActivity
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

    class SearchScreen(val search: String) : SupportAppScreen() {
        override fun getFragment() = SearchFragment.newInstance(search)
    }

    class HelpScreen() : SupportAppScreen() {
        override fun getFragment() = HelpFragment.newInstance()
    }

    class SettingsScreen() : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent =
            Intent(context, SettingsActivity::class.java)
    }

    class GeoScreen(val state:State?) : SupportAppScreen() {
        override fun getFragment() = GeoFragment.newInstance(state)
    }

    class FavoriteScreen() : SupportAppScreen() {
        override fun getFragment() = FavoriteFragment.newInstance()
    }

}