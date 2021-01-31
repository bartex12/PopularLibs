package com.example.popularlibs_homrworks.model.repositories.usersrepo

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.network.INetworkStatus
import com.example.popularlibs_homrworks.model.repositories.usersrepo.cashusers.IRoomGithubUsersCache
import io.reactivex.rxjava3.schedulers.Schedulers

//Получаем интерфейс через конструктор и пользуемся им для получения пользователей.
// Обратите внимание, что поток, на который мы подписываемся, указан именно тут.
// Таким образом, мы позволяем репозиторию самостоятельно следить за тем, чтобы сетевые вызовы
// выполнялись именно в io-потоке. Всегда лучше поступать именно таким образом, даже когда речь
// не идёт о сети — во избежание выполнения операций в неверном потоке в вызывающем коде.
class RetrofitGithubUsersRepo(val api:IDataSource,
                              val networkStatus: INetworkStatus,
                              var roomCash: IRoomGithubUsersCache):IGithubUsersRepo {

//    @Inject
//    lateinit var api:IDataSource
//    @Inject
//    lateinit var networkStatus: INetworkStatus
//    @Inject
//    lateinit var roomCash: IRoomGithubUsersCache

    //метод  интерфейса IGithubUsersRepo getUsers() - в зависимости от статуса сети
    //мы или получаем данные из сети, записывая их в базу данных с помощью Room через map
    //или берём из базы, преобразуя их также через map
    override fun getUsers() = networkStatus.isOnlineSingle()
        .flatMap {isOnline->  //получаем доступ к Boolean значениям
            if (isOnline){  //если сеть есть
                api.getUsers() //получаем данные из сети в виде Single<List<GithubUserRepos>>
                    .flatMap {users-> //получаем доступ к списку List<GithubUser>
                        //реализация кэширования списка пользователей из сети в базу данных
                        roomCash.doUsersCache(users)
                    }
            }else{
                //получение списка пользователей из кэша
                roomCash.getUsersFromCash()
            }
        }.subscribeOn(Schedulers.io())

}