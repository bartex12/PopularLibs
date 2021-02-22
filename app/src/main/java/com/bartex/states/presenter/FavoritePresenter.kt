package com.bartex.states.presenter

import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.presenter.base.BasePresenter
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class FavoritePresenter() : BasePresenter(){

    @Inject
    lateinit var roomCash: IRoomStateCash

    companion object{
        const val TAG = "33333"
    }

    //здесь надо добавить подписку, так как в roomCash она на сделана
    override fun getListData(): Single<List<State>> =
        roomCash. loadFavorite().subscribeOn(Schedulers.io())

    override fun navigateToScreen(state: State) {
        router.replaceScreen(Screens.DetailsScreen(state))
    }

    fun getPosition(): Int{
        return helper.getPositionFavorite()
    }

    fun savePosition(position: Int){
        helper.savePositionFavorite(position)
    }

    fun backPressed(): Boolean {
            router.exit()
            return true
        }
    }
