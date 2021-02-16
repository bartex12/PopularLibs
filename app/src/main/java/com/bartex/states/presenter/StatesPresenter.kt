package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.prefs.IPreferenceHelper
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.view.adapter.StatesItemView
import com.bartex.states.view.fragments.states.IStatesView
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

//презентер для работы с фрагментом StatesFragment,  Router для навигации
class StatesPresenter():MvpPresenter<IStatesView>() {

    @Inject
    lateinit var helper : IPreferenceHelper

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var statesRepo: IStatesRepo

    @Inject
    lateinit var router: Router

    val statesListPresenter =
        StatesListPresenter()

    class StatesListPresenter :
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
        loadData()

        //здесь присваиваем значение  слушателю щелчка по списку - ранее он был null
        statesListPresenter.itemClickListener = { itemView ->
            //переход на экран пользователя
            val state =  statesListPresenter.states[itemView.pos]
            helper.savePosition(itemView.pos) //сохраняем позицию
            Log.d(TAG, "StatesPresenter itemClickListener state name =${state.name}")
            router.navigateTo(Screens.DetailsScreen(state))
        }
    }

    //грузим данные и делаем сортировку в соответствии с настройками
    fun loadData() {
        val istSorted = helper.isSorted()
        val getSortCase = helper.getSortCase()
        var f_st:List<State>?= null
        statesRepo.getStates()
            .observeOn(Schedulers.computation())
            .flatMap {st->
                if(istSorted){
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
            .observeOn(mainThreadScheduler)
            .subscribe ({states->
                states?. let{Log.d(TAG, "StatesPresenter  loadData states.size = ${it.size}")}
                statesListPresenter.states.clear()
                states?. let{statesListPresenter.states.addAll(it)}
                viewState.updateList()
            }, {error -> Log.d(TAG, "StatesPresenter onError ${error.message}")
            })
    }

    fun getPosition(): Int{
        return helper.getPosition()
    }

    fun savePosition(position: Int){
        helper.savePosition(position)
    }

    fun backPressed(): Boolean {
        router.exit()
        router.exit() //выход с белого экрана App
        return true
    }
}