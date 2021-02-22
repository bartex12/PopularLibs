package com.bartex.states.presenter.base

import android.util.Log
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.prefs.IPreferenceHelper
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.presenter.StatesPresenter
import com.bartex.states.presenter.list.IStateListPresenter
import com.bartex.states.view.adapter.state.StatesItemView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

//презентер для работы с фрагментами  StatesFragment, SearchFragment
// FavoriteFragment сделан с другим адаптером
// Router для навигации
abstract class BasePresenter: MvpPresenter<IBaseView>() {

    @Inject
    lateinit var helper : IPreferenceHelper

    @Inject
    lateinit var mainThreadScheduler: Scheduler

    @Inject
    lateinit var statesRepo: IStatesRepo

    @Inject
    lateinit var router: Router

    companion object{
        const val TAG = "33333"
    }

    abstract fun getListData(): Single<List<State>>
    abstract fun navigateToScreen(state:State)

    val listPresenter =
        ListPresenter()

    class ListPresenter :
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
        listPresenter.itemClickListener = { itemView ->
            //переход на экран пользователя
            val state =  listPresenter.states[itemView.pos]
            helper.savePositionState(itemView.pos) //сохраняем позицию
            Log.d(TAG, "BasePresenter itemClickListener state name =${state.name}")
            navigateToScreen(state)
        }
    }

    //грузим данные и делаем сортировку в соответствии с настройками
    fun loadData() {
        val isSorted = helper.isSorted()
        val getSortCase = helper.getSortCase()
        var f_st:List<State>?= null
        Log.d(StatesPresenter.TAG, "BasePresenter  loadData isSorted = $isSorted getSortCase = $getSortCase")
        getListData()
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
            .observeOn(mainThreadScheduler)
            .subscribe ({states->
                states?. let{
                Log.d(StatesPresenter.TAG, "BasePresenter  loadData states.size = ${it.size}")}
                listPresenter.states.clear()
                states?. let{listPresenter.states.addAll(it)}
                viewState.updateList()
            }, {error -> Log.d(StatesPresenter.TAG, "BasePresenter onError ${error.message}")
            })
    }

}