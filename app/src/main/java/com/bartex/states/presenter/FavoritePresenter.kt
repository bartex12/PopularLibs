package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.prefs.IPreferenceHelper
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.view.adapter.StatesItemView
import com.bartex.states.view.fragments.favorite.IFavoriteView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class FavoritePresenter() : MvpPresenter<IFavoriteView>(){

    @Inject
    lateinit var roomCash: IRoomStateCash

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var helper : IPreferenceHelper

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    companion object{
        const val TAG = "33333"
    }

    val favoriteListPresenter =
        FavoriteListPresenter()

    class FavoriteListPresenter :
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
        loadFavorite()

        //здесь присваиваем значение  слушателю щелчка по списку - ранее он был null
        favoriteListPresenter.itemClickListener = { itemView ->
            //переход на экран пользователя
            val state = favoriteListPresenter.states[itemView.pos]
            helper.savePosition(itemView.pos) //сохраняем позицию
            Log.d(
                StatesPresenter.TAG,
                "StatesPresenter itemClickListener state name =${state.name}"
            )
            router.replaceScreen(Screens.DetailsScreen(state))
        }
    }

    private fun loadFavorite() {
        val isSorted = helper.isSorted()
        val getSortCase = helper.getSortCase()
        var f_st:List<State>?= null
        roomCash. loadFavorite()
            .observeOn(Schedulers.computation())
            .flatMap {st->
                if(isSorted){
                    if(getSortCase == 1){
                        f_st = st.filter {it.population!=null}.sortedByDescending {it.population}
                    }else if(getSortCase == 2){
                        f_st = st.filter {it.population!=null}.sortedBy {it.population}
                    }else if(getSortCase == 3){
                        f_st = st.filter {it.area!=null}.sortedByDescending {it.area}
                    }else if(getSortCase == 4){
                        f_st = st.filter {it.area!=null}.sortedBy {it.area}
                    }
                    return@flatMap Single.just(f_st)
                }else{
                    return@flatMap Single.just(st)
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(mainThreadScheduler)
            .subscribe ({states->
                states?. let{Log.d(TAG, "FavoritePresenter  loadData states.size = ${it.size}")}
                favoriteListPresenter.states.clear()
                states?. let{favoriteListPresenter.states.addAll(it)}
                viewState.updateList()
            }, {error -> Log.d(StatesPresenter.TAG, "FavoritePresenter onError ${error.message}")
            })
    }


    fun backPressed(): Boolean {
            router.exit()
            return true
        }
    }
