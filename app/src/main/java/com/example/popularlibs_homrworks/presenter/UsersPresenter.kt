package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.fragments.UsersView
import com.example.popularlibs_homrworks.model.GithubUser
import com.example.popularlibs_homrworks.model.GithubUsersRepo
import com.example.popularlibs_homrworks.view.adapter.UserItemView
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
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
            users[view.pos]
            view.setLogin(user.login)
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        //переход на экран детализации
        usersListPresenter.itemClickListener = { itemView ->
            //получение login через RxJava
            val disp =  Observable.just(usersListPresenter.users)
                .map {it[itemView.pos]}
                .map {it.login}
                .subscribe (
                    {router.replaceScreen(Screens.UserScreen(it))},
                    {Log.d(TAG, "UsersPresenter onError ${it.message}")})
            disp?.dispose() // уход на другой экран - отписка
        }
    }

    //всё в Main потоке, так как имитация данных
    fun loadData() {
        disposable =   usersRepo.getUsers()
            .subscribe(
                { user -> usersListPresenter.users.add(user)},
                {error -> Log.d(TAG, "UsersPresenter onError ${error.message}")},
                {Log.d(TAG, "UsersPresenter onCompleted  ")
                viewState.updateList() //обновляем после окончания передачи данных
                })
    }

    // возвращаемое значение нужно, чтобы в Activity мы могли определить,
    // было ли событие нажатия поглощено фрагментом или нужно обработать его стандартным способом.
    fun backPressed(): Boolean {
        router.exit()
        disposable?.dispose() //выход с экрана - отписка на всякий случай
        return true
    }

}
