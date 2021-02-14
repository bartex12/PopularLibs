package com.bartex.states.view.fragments.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.model.entity.state.State
import com.bartex.states.presenter.DetailsPresenter
import com.bartex.states.presenter.SearchPresenter
import com.bartex.states.view.adapter.StatesRVAdapter
import com.bartex.states.view.adapter.imageloader.GlideToVectorYouLoader
import com.bartex.states.view.fragments.BackButtonListener
import com.bartex.states.view.fragments.states.IStatesView
import com.bartex.states.view.fragments.weather.WeatherFragment
import com.bartex.states.view.main.TAG
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_states.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class SearchFragment(): MvpAppCompatFragment(),
    ISearchView,
    BackButtonListener {

    companion object {
        private const val ARG_SEARCH = "search"

        @JvmStatic
        fun newInstance(search: String) =
            SearchFragment()
                .apply {
                    arguments = Bundle().apply {
                        putString(ARG_SEARCH, search)
                    }
                }
    }

    val presenter: SearchPresenter by moxyPresenter {
        val search = arguments?.getString(ARG_SEARCH)
        Log.d(TAG, "SearchFragment SearchPresenter by moxyPresenter search = $search ")
        SearchPresenter(search).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_search, null)

    var adapter: StatesRVAdapter? = null

    override fun init() {
        Log.d(TAG, "SearchFragment init ")
        rv_search.layoutManager = LinearLayoutManager(context)
        adapter = StatesRVAdapter(
            presenter.searchListPresenter,
            GlideToVectorYouLoader(
                requireActivity()
            )
        )
        rv_search.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed(): Boolean = presenter.backPressed()

}