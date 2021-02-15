package com.bartex.states.view.fragments.geo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.model.entity.state.State
import com.bartex.states.presenter.GeoPresenter
import com.bartex.states.view.fragments.BackButtonListener
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class GeoFragment: MvpAppCompatFragment(),IGeoView, BackButtonListener {

    private var state: State? = null

    companion object{
        const val GEO_STATE = "GEO_STATE"

        fun newInstance(state: State?):GeoFragment =
            GeoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(GEO_STATE, state )}
            }
    }

    val presenter: GeoPresenter by moxyPresenter {
        arguments?.let {state = it.getParcelable<State>(GEO_STATE)}
        GeoPresenter(state).apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        return inflater.inflate(R.layout.fragment_geo,container, false )
    }

    override fun backPressed(): Boolean = presenter.backPressed()

}