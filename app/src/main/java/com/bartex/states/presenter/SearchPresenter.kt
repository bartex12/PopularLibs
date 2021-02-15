package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.prefs.IPreferenceHelper
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.view.adapter.StatesItemView
import com.bartex.states.view.fragments.search.ISearchView

import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class SearchPresenter( val search:String? ): MvpPresenter<ISearchView>() {

    @Inject
    lateinit var helper : IPreferenceHelper

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

        //переход на экран списка репозиториев
        searchListPresenter.itemClickListener = { itemView ->
            val state = searchListPresenter.states[itemView.pos]
            helper.savePositionSearch(itemView.pos) //сохраняем позицию
            router.navigateTo(Screens.DetailsScreen(state))
        }
    }

    fun searchData() {
        val istSorted = helper.istSorted()
        val getSortCase = helper.getSortCase()
        var f_st:List<State>?= null
        search?. let{search->
            statesRepo.searchStates(search)
                .observeOn(Schedulers.computation())
                .flatMap {state->
                    if(istSorted){
                        if(getSortCase == 1){
                            f_st = state.filter {it.population!=null}.sortedByDescending {it.population}
                        }else if(getSortCase == 2){
                            f_st = state.filter {it.population!=null}.sortedBy {it.population}
                        }else if(getSortCase == 3){
                            f_st = state.filter {it.area!=null}.sortedByDescending {it.area}
                        }else if(getSortCase == 4){
                            f_st = state.filter {it.area!=null}.sortedBy {it.area}
                        }
                    }
                    return@flatMap Single.just(f_st)
                }
                .observeOn(mainThreadScheduler)
                .subscribe ({states->
                    states?. let{ Log.d(TAG, "SearchPresenter  loadData states_search.size = ${it.size}")}
                    searchListPresenter.states.clear()
                    states?. let{searchListPresenter.states.addAll(it)}
                    viewState.updateList()
                }, {error -> Log.d(TAG, "SearchPresenter onError ${error.message}")
                })
        } ?: Log.d(TAG, "SearchPresenter searchData search = null")
    }

    fun getPositionSearch(): Int{
        return helper.getPositionSearch()
    }

    fun savePositionSearch(position: Int){
        helper.savePositionSearch(position)
    }

    fun backPressed():Boolean {
        //преход на экран StatesScreen
        router.backTo(Screens.StatesScreen())
        Log.d(TAG, "SearchPresenter backPressed ")
        return true
    }
}