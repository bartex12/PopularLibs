package com.bartex.states

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.bartex.states.model.entity.state.State
import com.bartex.states.view.fragments.details.DetailsFragment
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) //эта аннотация не обязательна
class DetailsFragmentEspressoTest {

    private lateinit var  scenario : FragmentScenario<DetailsFragment>

    @Before
    fun setup(){
        //Запускаем Fragment в корне Activity
        //launchFragmentInContainer() нужен для запуска Фрагмента с UI;
        //launchFragment — для Фрагментов без UI.
        scenario = launchFragmentInContainer()
    }

    @Test
    //создаем бандл с аргументами и создаем новый сценарий, который принимает аргументы.
    //сценарий по умолчанию переводит Фрагмент в State.RESUMED, тут это написано просто в качестве примера.
//Далее мы проверяем, что отображается строка в соответствии с переданными аргументами.
    fun fragment_testBundle() {

        val state = State(capital = "Moscow", flag = "", name = "Russia", region = "Europe",
            population = 146000000, area = 17000000f, latlng = arrayOf(60f, 40f) )
        //Можно передавать аргументы во Фрагмент, но это необязательно
        val fragmentArgs = bundleOf(DetailsFragment.ARG_STATE to state)
        //Запускаем Fragment с аргументами
        val scenario = launchFragmentInContainer<DetailsFragment>(fragmentArgs)
        //Возможность менять стейт Фрагмента
        scenario.moveToState(Lifecycle.State.RESUMED)

        //на вью  с id = tv_state_name проверить соответствие тексту "Russia"
        Espresso.onView(withId(R.id.tv_state_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("Russia")))
        Espresso.onView(withId(R.id.tv_state_region))
            .check(ViewAssertions.matches(ViewMatchers.withText("Регион:   EUROPE ")))
        Espresso.onView(withId(R.id.tv_state_area))
            .check(ViewAssertions.matches(ViewMatchers.withText("Площадь: 17,0 млн. кв. км.")))
        Espresso.onView(withId(R.id.tv_state_population))
            .check(ViewAssertions.matches(ViewMatchers.withText("Население: 146,0 млн. чел.")))
        Espresso.onView(withId(R.id.tv_state_capital))
            .check(ViewAssertions.matches(ViewMatchers.withText("Столица:   MOSCOW ")))
    }

}