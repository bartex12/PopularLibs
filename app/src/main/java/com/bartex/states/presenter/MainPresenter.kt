package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.view.main.MainView
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var  roomCash: IRoomStateCash

    companion object{
        const val TAG = "33333"
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.navigateTo(Screens.StatesScreen())
    }

    fun backClicked() {
        router.exit()
    }

   fun  doSearch(search:String){
       Log.d(TAG, "MainPresenter doSearch search = $search ")
       router.navigateTo(Screens.SearchScreen(search))
   }

    fun  showHelp(){
        Log.d(TAG, "MainPresenter showHelp")
        router.navigateTo(Screens.HelpScreen())
    }

    fun showSettingsActivity(){
        router.navigateTo(Screens.SettingsScreen())
    }

    fun addToFavorite(){
        roomCash.
    }

    fun removeFromFavorite(){

    }


}