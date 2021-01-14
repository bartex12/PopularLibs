package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.fragments.UsersView
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.view.adapter.UserItemView
import com.example.popularlibs_homrworks.view.main.TAG
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router
import rx.Observer


//презентер для работы с фрагментом UsersFragment,  Router для навигации
class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router):
    MvpPresenter<UsersView>() {

    val usersListPresenter =  UsersListPresenter()

    //вложенный класс для работы с адаптером
    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        usersListPresenter.itemClickListener = { itemView ->
            //переход на экран пользователя
           val login =  usersListPresenter.users[itemView.pos].login
            Log.d(TAG, "UsersPresenter itemClickListener login =$login")
            router.replaceScreen(Screens.UserScreen(login))
        }
    }

    fun loadData() {
        usersRepo.getUsers()
            .subscribe(UserObserver())
        viewState.updateList() //обновляем после окончания передачи данных
    }

    //inner - для того, чтобы обеспечить доступ к переменным класса UsersPresenter
    //Класс может быть отмечен как внутренний с помощью слова inner, тем самым он будет иметь
    // доступ к членам внешнего класса. Внутренние классы содержат ссылку на объект внешнего класс
    inner class UserObserver:Observer<GithubUser> {

        override fun onNext(user: GithubUser?) {
            user?. let {usersListPresenter.users.add(user)}
        }

        override fun onError(e: Throwable?) {
            Log.d(TAG, "UsersPresenter onError $e ")
        }

        override fun onCompleted() {
            Log.d(TAG, "UsersPresenter onCompleted  ")
        }
    }
    //Методичка:
    //Для обработки нажатия клавиши «Назад» добавлена функция backPressed(), возвращающая Boolean,
    // в которой мы передаем обработку выхода с экрана роутеру. Вообще, функции Presenter,
    // согласно парадигме, не должны ничего возвращать, но в данном случае приходится
    // идти на компромисс из-за недостатков фреймворка. Дело в том, что у фрагмента
    // нет своего коллбэка для обработки перехода назад и нам придется пробрасывать его из Activity.
    // А возвращаемое значение нужно, чтобы в Activity мы могли определить,
    // было ли событие нажатия поглощено фрагментом или нужно обработать его стандартным способом.
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
