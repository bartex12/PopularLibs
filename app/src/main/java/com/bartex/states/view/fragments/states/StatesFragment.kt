package com.bartex.states.view.fragments.states

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.model.api.state.ApiHolderState
import com.bartex.states.model.network.NetworkStatus
import com.bartex.states.model.repositories.states.StatesRepo
import com.bartex.states.model.repositories.states.cash.RoomStateCash
import com.bartex.states.model.room.Database
import com.bartex.states.presenter.states.StatesPresenter
import com.bartex.states.view.adapter.StatesRVAdapter
import com.bartex.states.view.adapter.imageloader.GlideToVectorYouLoader
import com.bartex.states.view.fragments.BackButtonListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_states.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class StatesFragment : MvpAppCompatFragment(),
    IStatesView,
    BackButtonListener {

    companion object { fun newInstance() =
        StatesFragment()
    }

    val presenter: StatesPresenter by moxyPresenter {
        StatesPresenter(
            AndroidSchedulers.mainThread(),
            StatesRepo(ApiHolderState.api, NetworkStatus(App.instance),
            Database.getInstance(), RoomStateCash()),
            App.instance.router
        )
    }

    var adapter: StatesRVAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_states, null)

    override fun init() {
        rv_states.layoutManager = LinearLayoutManager(context)
        adapter = StatesRVAdapter(
            presenter.statesListPresenter,
            GlideToVectorYouLoader(
                requireActivity()
            )
        )
        rv_states.adapter = adapter
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

}