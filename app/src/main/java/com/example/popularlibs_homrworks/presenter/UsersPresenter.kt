package com.example.popularlibs_homrworks.presenter

import android.util.Log
import com.example.popularlibs_homrworks.Screens
import com.example.popularlibs_homrworks.view.fragments.UsersView
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.repository.IGithubUsersRepo
import com.example.popularlibs_homrworks.view.adapter.UserItemView
import com.example.popularlibs_homrworks.view.main.TAG
import io.reactivex.rxjava3.core.Scheduler
import moxy.MvpPresenter
import ru.terrakok.cicerone.Router

//презентер для работы с фрагментом UsersFragment,  Router для навигации
class UsersPresenter(val mainThreadScheduler: Scheduler, val usersRepo: IGithubUsersRepo,
 val router: Router): MvpPresenter<UsersView>() {

    val usersListPresenter =  UsersListPresenter()

    //вложенный класс для работы с адаптером
    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()
        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun getCount() = users.size

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            user.login?. let{view.setLogin(it)}
            user.avatarUrl?. let{view.loadAvatar(it) }
        }
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.init()
        loadData()

        //переход на экран детализации
        usersListPresenter.itemClickListener = { itemView ->
            //получение login через RxJava
            val user = usersListPresenter.users[itemView.pos]
            router.navigateTo(Screens.UserScreen(user))
        }
    }

//можно же в loadData() вместо usersRepo.getUsers() написать просто
//ApiHolder.api.getUsers().subscribeOn(Schedulers.io())
//Зачем плодить дополнительные класс и интерфейс репозитория
//Или это заготовка на будущее?
    fun loadData() {
        //ApiHolder.api.getUsers().subscribeOn(Schedulers.io()) //Блин, так же проще
        usersRepo.getUsers()
            .observeOn(mainThreadScheduler)
            .subscribe({ repos ->
                usersListPresenter.users.clear()
                    usersListPresenter.users.addAll(repos)
                    viewState.updateList()
                },{error -> Log.d(TAG, "UsersPresenter onError ${error.message}")})
    }

    // возвращаемое значение нужно, чтобы в Activity мы могли определить,
    // было ли событие нажатия поглощено фрагментом или нужно обработать его стандартным способом.
    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}
