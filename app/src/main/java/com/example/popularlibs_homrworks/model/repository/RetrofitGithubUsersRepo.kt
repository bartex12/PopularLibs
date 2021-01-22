package com.example.popularlibs_homrworks.model.repository

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.api.IDataSource
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//Получаем интерфейс через конструктор и пользуемся им для получения пользователей.
// Обратите внимание, что поток, на который мы подписываемся, указан именно тут.
// Таким образом, мы позволяем репозиторию самостоятельно следить за тем, чтобы сетевые вызовы
// выполнялись именно в io-потоке. Всегда лучше поступать именно таким образом, даже когда речь
// не идёт о сети — во избежание выполнения операций в неверном потоке в вызывающем коде.
class RetrofitGithubUsersRepo(val api:IDataSource):IGithubUsersRepo {

    override fun getUsers(): Single<List<GithubUser>> =
        api.getUsers().subscribeOn(Schedulers.io())

}