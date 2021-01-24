package com.example.popularlibs_homrworks.model.repositories.users

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.room.database.Database
import com.example.popularlibs_homrworks.model.room.network.INetworkStatus
import com.example.popularlibs_homrworks.model.room.tablesroom.RoomGithubUser
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//Получаем интерфейс через конструктор и пользуемся им для получения пользователей.
// Обратите внимание, что поток, на который мы подписываемся, указан именно тут.
// Таким образом, мы позволяем репозиторию самостоятельно следить за тем, чтобы сетевые вызовы
// выполнялись именно в io-потоке. Всегда лучше поступать именно таким образом, даже когда речь
// не идёт о сети — во избежание выполнения операций в неверном потоке в вызывающем коде.
class RetrofitGithubUsersRepo(val api:IDataSource, val networkStatus:INetworkStatus,
 val db: Database):IGithubUsersRepo {
    //метод  интерфейса IGithubUsersRepo getUsers() - в зависимости от статуса сети
    //мы или получаем данные из сети, записывая их в базу данных с помощью Room через map
    //или берём из базы, преобразуя их также через map
    override fun getUsers() = networkStatus.isOnlineSingle()
        .flatMap {isOnline->  //получаем доступ к Boolean значениям
            if (isOnline){  //если сеть есть
                api.getUsers() //получаем данные из сети в виде Single<List<GithubUserRepos>>
                    .flatMap {users-> //получаем доступ к списку List<GithubUser>
                        //*можно метод Single.fromCallable{users} написать после действий с базой
                       Single.fromCallable{ //создаём обратно Single из списка, по пути пишем в базу
                           // map для базы, так как классы разные
                            val roomUsers = users.map {user->
                                RoomGithubUser(user.id?:"", user.login?:"",
                                    user.avatarUrl?:"", user.repos_url?:"" )
                            }
                            db.userDao.insert(roomUsers) //пишем в базу
                            return@fromCallable  users //возвращаем users опять в виде Single<List<GithubUserRepos>>
                        }
                    }
            }else{
                Single.fromCallable {
                    db.userDao.getAll().map {roomUser->
                        GithubUser(roomUser.id, roomUser.login,
                            roomUser.avatarUrl, roomUser.repos_url)
                    }
                }
            }
        }.subscribeOn(Schedulers.io())

}