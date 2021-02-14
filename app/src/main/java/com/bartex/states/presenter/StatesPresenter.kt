package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.states.IStatesRepo
import com.bartex.states.view.adapter.StatesItemView
import com.bartex.states.view.fragments.states.IStatesView
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

//презентер для работы с фрагментом StatesFragment,  Router для навигации
class StatesPresenter:MvpPresenter<IStatesView>() {

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
            viewState.savePosition(itemView.pos)
            Log.d(TAG, "StatesPresenter itemClickListener state name =${state.name}")
            router.navigateTo(Screens.DetailsScreen(state))
        }
    }

    private fun loadData() {
        statesRepo.getStates()
            .observeOn(mainThreadScheduler)
            .subscribe ({states->
                Log.d(TAG, "StatesPresenter  loadData states_search.size = ${states.size}")
                statesListPresenter.states.clear()
                statesListPresenter.states.addAll(states)
                viewState.updateList()
            }, {error -> Log.d(TAG, "StatesPresenter onError ${error.message}")
            })
    }

    fun backPressed(): Boolean {
        router.exit()
        router.exit() //выход с белого экрана App
        return true
    }
}