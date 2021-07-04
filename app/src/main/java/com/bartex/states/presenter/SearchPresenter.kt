package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.entity.state.State
import com.bartex.states.presenter.base.BasePresenter
import io.reactivex.rxjava3.core.Single

class SearchPresenter( val search:String? ): BasePresenter() {

    companion object{
        const val TAG = "33333"
    }

    override fun getListData(): Single<List<State>> {
        return if(search!=null){
            statesRepo.searchStates(search)
        }else{
            Single.just(listOf<State>())
        }
    }

    override fun navigateToScreen(state: State) {
        router.navigateTo(Screens.DetailsScreen(state))
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