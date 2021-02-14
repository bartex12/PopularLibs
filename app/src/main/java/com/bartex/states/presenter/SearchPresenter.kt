package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.view.adapter.StatesItemView
import com.bartex.states.view.fragments.search.ISearchView

import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchPresenter( val search:String? ): MvpPresenter<ISearchView>() {

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var statesRepo: IStatesRepo

    @Inject
    lateinit var router: Router

    val searchListPresenter =
        SearchListPresenter()

    class SearchListPresenter :
        IStateListPresenter {

        val states = mutableListOf<State>()

        override var itemClickListener: ((StatesItemView) -> Unit)? = null

        override fun getCount() = states.size

        override fun bindView(view: StatesItemView) {
            val state = states[view.pos]
            state.name?. let{view.setName(it)}
            state.flag?. let{view.loadFlag(it)}
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        searchData()
    }

    private fun searchData() {
        search?. let{
            statesRepo.searchStates(it)
                .observeOn(mainThreadScheduler)
                .subscribe ({states->
                    Log.d(TAG, "SearchPresenter  loadData states_search.size = ${states.size}")
                    searchListPresenter.states.clear()
                    searchListPresenter.states.addAll(states)
                    viewState.updateList()
                }, {error -> Log.d(TAG, "SearchPresenter onError ${error.message}")
                })
        } ?: Log.d(TAG, "SearchPresenter searchData search = null")
    }

    fun backPressed():Boolean {
        //преход на экран StatesScreen
        router.backTo(Screens.StatesScreen())
        Log.d(TAG, "SearchPresenter backPressed ")
        return true
    }
}