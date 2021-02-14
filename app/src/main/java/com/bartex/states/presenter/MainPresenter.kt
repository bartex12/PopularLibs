package com.bartex.states.presenter

import android.util.Log
import com.bartex.states.Screens
import com.bartex.states.view.main.MainView
import com.bartex.states.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class MainPresenter: MvpPresenter<MainView>() {

    @Inject
    lateinit var router: Router

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
}