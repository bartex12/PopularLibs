package com.bartex.states.view.main

import android.content.Intent
import android.content.Intent.createChooser
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.core.view.GravityCompat
import com.bartex.states.App
import com.bartex.states.R
import com.bartex.states.presenter.MainPresenter
import com.bartex.states.view.fragments.BackButtonListener
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject
 


class MainActivity: MvpAppCompatActivity(),
    MainView, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

    companion object{
        const val TAG = "33333"
    }

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

        setSupportActionBar(toolbar) //поддержка экшенбара для создания строки поиска
        //drawer_layout.openDrawer(GravityCompat.START) //шторка в открытом состоянии
        val toggle = ActionBarDrawerToggle(this,drawer_layout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close )//гамбургер
        drawer_layout.addDrawerListener(toggle) //слушатель гамбургера
        toggle.syncState() //синхронизация гамбургера

        nav_view.setNavigationItemSelectedListener(this) //слушатель меню шторки

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id){
           R.id.navigation_settings ->{
               presenter. showSettingsActivity()
           }
            R.id.navigation_help->{
                presenter.showHelp()
            }
        }
        return super.onOptionsItemSelected(item)
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
        Log.d(TAG, "MainActivity onQueryTextSubmit query = $query ")
        query?. let{presenter.doSearch(it)}
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_favorites -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_favorites")
                //todo
            }
            R.id.nav_setting -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_setting")
                presenter. showSettingsActivity()
            }
            R.id.nav_help -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_help")
                presenter.showHelp()
            }

            R.id.nav_share -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_share")
                //поделиться - передаём ссылку на приложение в маркете
                shareApp()
            }
            R.id.nav_rate -> {
                Log.d(TAG, "MainActivity onNavigationItemSelected nav_send")
                //оценить приложение - попадаем на страницу приложения в маркете
                rateApp()
            }
            // Выделяем выбранный пункт меню в шторке
        }
        // Выделяем выбранный пункт меню в шторке
        item.isChecked = true
        drawer_layout.closeDrawer(GravityCompat.START)
        return false
    }

    private fun shareApp() {
        val sendIntent = Intent()
        with(sendIntent){
            type = "text/plain"
            //чтобы отменить неправильный вариант Запомнить выбор - убрать и вставить эту строку
            action = Intent.ACTION_SEND_MULTIPLE //задаём возможность отправки несколькими способами
            putExtra(Intent.EXTRA_TEXT,
                """ ${getString(R.string.app_name)}
                    ${getString(R.string.uri_stor)}
                """.trimIndent()
            )
        }
        startActivity(sendIntent)
    }

    private fun rateApp() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(getString(R.string.uri_stor))
        startActivity(intent)
    }

}