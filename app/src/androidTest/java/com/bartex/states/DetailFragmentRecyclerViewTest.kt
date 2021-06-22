package com.bartex.states

import android.content.Context
import android.content.Intent
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.bartex.states.view.adapter.state.StatesRVAdapter
import com.bartex.states.view.fragments.states.StatesFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailFragmentRecyclerViewTest {

    companion object {
        private const val TIMEOUT = 15000L
    }

    //Класс UiDevice предоставляет доступ к вашему устройству.
    //Именно через UiDevice вы можете управлять устройством, открывать приложения
    //и находить нужные элементы на экране
    private val uiDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    //Контекст нам понадобится для запуска нужных экранов и получения packageName
    private val context = ApplicationProvider.getApplicationContext<Context>()
    //Путь к классам нашего приложения, которые мы будем тестировать
    private val packageName = context.packageName

    //private lateinit var  scenario : FragmentScenario<StatesFragment>

    @Before
    fun setup(){
//        //Запускаем Fragment в корне Activity
//        //launchFragmentInContainer() нужен для запуска Фрагмента с UI;
//        //launchFragment — для Фрагментов без UI.
//        scenario = launchFragmentInContainer()
        //Для начала сворачиваем все приложения, если у нас что-то запущено
        uiDevice.pressHome()

        //Запускаем наше приложение
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        //Мы уже проверяли Интент на null в предыдущем тесте, поэтому допускаем, что Интент у нас не null
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)//Чистим бэкстек от запущенных ранее Активити
        context.startActivity(intent)

       // scenario = launchFragmentInContainer()
        //Ждем, когда приложение откроется на смартфоне чтобы начать тестировать его элементы
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    @Test
    fun activitySearch_ScrollTo() {
        //scenario.moveToState(Lifecycle.State.RESUMED)
            //находим наш RecyclerView по id и вызываем у него Action scrollTo, который стал
            // нам доступен благодаря новой зависимости RecyclerViewActions.
            // Метод hasDescendant ищет вью с надписью “Canada”, то есть 2 элемент списка.
            // И если находит, то проматывает список до этого элемента.
            // То же самое можно сделать с помощью метода scrollToPosition,
            // передавая индекс нужного элемента в качестве аргумента.
            Espresso.onView(withId(R.id.rv_states))
                .perform(
                    RecyclerViewActions.scrollTo<StatesRVAdapter.ViewHolder>(
                        ViewMatchers.hasDescendant(ViewMatchers.withText("Canada"))
                    ))
    }

    @Test
    //метод, который нажимает на элемент списка:
    //Тут мы вызываем метод actionOnItemAtPosition, который в качестве аргументов принимает
    // позицию элемента, с которым мы хотим взаимодействовать, и Action. Если запустить тест,
    // то вы увидите всплывающее уведомление, позволяющее вам самостоятельно наблюдать
    // процесс исполнения теста.
    fun statesFragment_PerformClickAtPosition() {

            Espresso.onView(withId(R.id.rv_states))
                .perform(
                    RecyclerViewActions.actionOnItemAtPosition<StatesRVAdapter.ViewHolder>(
                        1,
                        ViewActions.click()
                    )
                )
    }
}