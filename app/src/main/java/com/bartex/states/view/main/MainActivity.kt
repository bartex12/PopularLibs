package com.bartex.states.view.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.Screens
import com.bartex.states.presenter.MainPresenter
import com.bartex.states.view.fragments.BackButtonListener
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


const val TAG = "33333"

class MainActivity: MvpAppCompatActivity(),
    MainView, SearchView.OnQueryTextListener {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    val navigator = SupportAppNavigator(this, supportFragmentManager,
        R.id.container
    )

    val presenter: MainPresenter by moxyPresenter {
        MainPresenter().apply {  App.instance.appComponent.inject(this) }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "MainActivity onCreate ")
        setContentView(R.layout.activity_main)
        App.instance.appComponent.inject(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "MainActivity onCreateOptionsMenu ")
        menuInflater.inflate(R.menu.states_search, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchView =searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    //при нажатии на кнопку Назад если фрагмент реализует BackButtonListener, вызываем метод backPressed
    //при этом если мы в списке- выходим из приложения, а если в пользователе - возвращаемся в список
    override fun onBackPressed() {
        supportFragmentManager.fragments.forEach {
            if(it is BackButtonListener && it.backPressed()){
                return@onBackPressed
            }
        }
        Log.d(TAG, "MainActivity onBackPressed after if ")
        presenter.backClicked()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        //todo
        Log.d(TAG, "MainActivity onQueryTextSubmit query = $query ")
        query?. let{presenter.doSearch(it)}
       // query?. let{router.navigateTo(Screens.SearchScreen(it))}
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        //todo
        //Log.d(TAG, "MainActivity onQueryTextSubmit newText = $newText ")
        return false
    }

}