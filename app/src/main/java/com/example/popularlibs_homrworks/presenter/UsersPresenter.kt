package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.fragments.UsersView
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.view.adapter.UserItemView
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router



//презентер для работы с фрагментом UsersFragment,  Router для навигации
class UsersPresenter(val usersRepo: GithubUsersRepo, val router: Router):
    MvpPresenter<UsersView>() {

    val usersListPresenter =  UsersListPresenter()
    var disposable: Disposable? = null

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
            disposable?.dispose()
        }
    }

    fun loadData() {
        disposable =   usersRepo.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
            { user -> usersListPresenter.users.add(user)},
            {error -> Log.d(TAG, "UsersPresenter onError $error ")},
            {Log.d(TAG, "UsersPresenter onCompleted  ")
             viewState.updateList() //обновляем после окончания передачи данных
            })
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
        disposable?.dispose()
        return true
    }

}
