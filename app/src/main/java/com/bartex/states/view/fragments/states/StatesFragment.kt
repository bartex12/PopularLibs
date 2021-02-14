package com.bartex.states.view.fragments.states

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.presenter.StatesPresenter
import com.bartex.states.view.adapter.StatesRVAdapter
import com.bartex.states.view.adapter.imageloader.GlideToVectorYouLoader
import com.bartex.states.view.fragments.BackButtonListener
import com.bartex.states.view.main.TAG
import kotlinx.android.synthetic.main.fragment_states.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class StatesFragment : MvpAppCompatFragment(),
    IStatesView,
    BackButtonListener {

    private var position = 0
    var adapter: StatesRVAdapter? = null

    companion object {
        fun newInstance() = StatesFragment()
    }

    val presenter: StatesPresenter by moxyPresenter {
        StatesPresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_states, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "StatesFragment onViewCreated ")
        //восстанавливаем позицию списка после поворота или возвращения на экран
        position = presenter.getPosition()
    }

    //запоминаем  позицию списка, на которой сделан клик - на случай поворота экрана
    override fun onPause() {
        super.onPause()
        //определяем первую видимую позицию
        val manager = rv_states.layoutManager as LinearLayoutManager
        val firstPosition = manager.findFirstVisibleItemPosition()
        presenter.savePosition(firstPosition)
    }

    override fun init() {
        rv_states.layoutManager = LinearLayoutManager(context)
        adapter = StatesRVAdapter(
            presenter.statesListPresenter,
            GlideToVectorYouLoader(
                requireActivity()
            )
        )
        rv_states.adapter = adapter
        rv_states.layoutManager?.scrollToPosition(position) //крутим в запомненную позицию списка
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun backPressed() = presenter.backPressed()

}