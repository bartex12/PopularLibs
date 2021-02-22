package com.bartex.states.view.fragments.favorite

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.presenter.FavoritePresenter
import com.bartex.states.presenter.base.IBaseView
import com.bartex.states.view.adapter.StatesRVAdapter
import com.bartex.states.view.adapter.imageloader.GlideToVectorYouLoader
import com.bartex.states.view.fragments.BackButtonListener
import kotlinx.android.synthetic.main.fragment_favorite.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class FavoriteFragment: MvpAppCompatFragment(), IBaseView,
    BackButtonListener {

    private var position = 0
    var adapter: StatesRVAdapter? = null

    companion object {
        const val TAG = "33333"

        fun newInstance() = FavoriteFragment()
    }

    val presenter: FavoritePresenter by moxyPresenter {
        FavoritePresenter().apply {
            App.instance.appComponent.inject(this)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        View.inflate(context, R.layout.fragment_favorite, null)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "FavoriteFragment onViewCreated ")
        //восстанавливаем позицию списка после поворота или возвращения на экран
        position = presenter.getPosition()
        //приводим меню тулбара в соответствии с onPrepareOptionsMenu в MainActivity
        setHasOptionsMenu(true)
        requireActivity().invalidateOptionsMenu()
    }

    //запоминаем  позицию списка, на которой сделан клик - на случай поворота экрана
    override fun onPause() {
        super.onPause()
        Log.d(TAG, "FavoriteFragment onPause ")
        //определяем первую видимую позицию
        val manager = rv_favorite.layoutManager as LinearLayoutManager
        val firstPosition = manager.findFirstVisibleItemPosition()
        presenter.savePosition(firstPosition)
    }

    override fun init() {
        rv_favorite.layoutManager = LinearLayoutManager(context)
        adapter = StatesRVAdapter(
            presenter.listPresenter,
            GlideToVectorYouLoader(
                requireActivity()
            )
        )
        rv_favorite.adapter = adapter
        rv_favorite.layoutManager?.scrollToPosition(position) //крутим в запомненную позицию списка
        Log.d(TAG, "FavoriteFragment init scrollToPosition = $position")
    }

    override fun updateList() {
        if(presenter.listPresenter.states.isEmpty()){
            rv_favorite.visibility = View.GONE
            empty_view_favorite.visibility = View.VISIBLE
        }else{
            rv_favorite.visibility =  View.VISIBLE
            empty_view_favorite.visibility =View.GONE

            adapter?.notifyDataSetChanged()
        }
    }

    override fun backPressed(): Boolean = presenter.backPressed()

}