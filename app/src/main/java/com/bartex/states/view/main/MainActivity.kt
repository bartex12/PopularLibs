package com.bartex.states.view.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.bartex.states.view.fragments.details.DetailsFragment
import com.bartex.states.view.fragments.favorite.FavoriteFragment
import com.bartex.states.view.fragments.geo.GeoFragment
import com.bartex.states.view.fragments.search.SearchFragment
import com.bartex.states.view.fragments.states.StatesFragment
import com.bartex.states.view.fragments.weather.WeatherFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import moxy.MvpAppCompatActivity
import moxy.ktx.moxyPresenter
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject


class MainActivity: MvpAppCompatActivity(),
    MainView, SearchView.OnQueryTextListener, NavigationView.OnNavigationItemSelectedListener {

    private var doubleBackToExitPressedOnce = false

    var toggle:ActionBarDrawerToggle? = null

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
        toggle = ActionBarDrawerToggle(this,drawer_layout,
                toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close )//гамбургер
        toggle?. let{ drawer_layout.addDrawerListener(it)}  //слушатель гамбургера
        toggle?.syncState() //синхронизация гамбургера

        //https://stackoverflow.com/questions/28531503/toolbar-switching-from-drawer-to-back-
        // button-with-only-one-activity/29292130#29292130
        //если в BackStack больше одного фрагмента (там почему то всегда есть 1 фрагмент)
        //то отображаем стрелку назад и устанавливаем слушатель на щелчок по ней с действием
        //onBackPressed(), иначе отображаем гамбургер и по щелчку открываем шторку
        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 1){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            }else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                toggle?.syncState()
                toolbar.setNavigationOnClickListener {
                    drawer_layout.openDrawer(GravityCompat.START)
                }
            }
        }

        nav_view.setNavigationItemSelectedListener(this) //слушатель меню шторки
        App.instance.appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "MainActivity onResume ")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Log.d(TAG, "MainActivity onCreateOptionsMenu ")
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.search)
        val searchView =searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        supportFragmentManager.findFragmentById(R.id.container)?. let{
            menu?.findItem(R.id.search)?.isVisible = it is StatesFragment
            menu?.findItem(R.id.favorites)?.isVisible = it !is FavoriteFragment && it !is WeatherFragment
        }

        toolbar.title = when(supportFragmentManager.findFragmentById(R.id.container)){
            is StatesFragment -> getString(R.string.app_name)
            is SearchFragment -> getString(R.string.search_name)
            is WeatherFragment -> getString(R.string.weather_name)
            is DetailsFragment -> getString(R.string.details_name)
            is FavoriteFragment -> getString(R.string.favorite_name)
            is GeoFragment -> getString(R.string.geo_name)
            else -> getString(R.string.app_name)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id){
            R.id.favorites ->{
                presenter.showFavorites()
                return true
            }
           R.id.navigation_settings ->{
               presenter. showSettingsActivity()
               return true
           }
            R.id.navigation_help->{
                presenter.showHelp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "MainActivity onPause ")
        navigatorHolder.removeNavigator()
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
                presenter.showFavorites()
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

    //при нажатии на кнопку Назад если фрагмент реализует BackButtonListener, вызываем метод backPressed
    //при этом если мы в списке стран - выходим из приложения по двойному щелчку,
    // а если в другом экране - делаем то, что там прописано
    override fun onBackPressed() {
        //если мы в StatesFragment, то при нажатии Назад показываем Snackbar и при повторном
        //нажати в течении 2 секунд закрываем приложение
        if( supportFragmentManager.findFragmentById(R.id.container) is StatesFragment){
            Log.d(TAG, "MainActivity onBackPressed  это StatesFragment")
            //если флаг = true - а это при двойном щелчке - закрываем программу
            if (doubleBackToExitPressedOnce) {
                Log.d(TAG, "MainActivity onBackPressed  doubleBackToExitPressedOnce")
                presenter.backClicked()
                return
            }
            doubleBackToExitPressedOnce = true //выставляем флаг = true
            //закрываем шторку, если была открыта
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            //показываем Snackbar: Для выхода нажмите  НАЗАД  ещё раз
            Snackbar.make(
                findViewById(android.R.id.content), this.getString(R.string.forExit),
                Snackbar.LENGTH_SHORT).show()
            //запускаем поток, в котором через 2 секунды меняем флаг
            Handler(Looper.getMainLooper())
                .postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
            //если мы НЕ в StatesFragment, то при нажатии Назад вызываем backPressed() фрагмента
            //и делаем то, что там прописано
        }else{
            Log.d(TAG, "MainActivity onBackPressed  это НЕ StatesFragment")
            supportFragmentManager.fragments.forEach {
                if(it is BackButtonListener && it.backPressed()){
                    return@onBackPressed
                }
            }
        }
    }

}