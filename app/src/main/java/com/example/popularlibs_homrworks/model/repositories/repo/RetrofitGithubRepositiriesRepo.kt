package com.example.popularlibs_homrworks.model.repositories.repo

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.room.database.Database
import com.example.popularlibs_homrworks.model.room.network.INetworkStatus
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositiriesRepo(val api: IDataSource, val networkStatus: INetworkStatus,
    val db: Database):IGithubRepositoriesRepo {

    override fun getUserRepo(user: GithubUser): Single<List<GithubUserRepos>> =
        networkStatus.isOnlineSingle().flatMap {isOnLine->
            //если сеть есть, то
                if (isOnLine){
                    //если repos_url есть,получаем репозитории, пишем их в базу
                    //а если нет -  делаем вызов Single.error
                    user.repos_url?. let{url->
                        api.getUserRepos(url) //получаем репозитории в виде  Single<List<GithubUserRepos>>
                            .flatMap {repos->  //доступ к List<GithubUserRepos>
                                Single.fromCallable { //возвращаемся к Single<List<GithubUserRepos>>
                                    //получаем в базе юзера по его логину -для того, чтобы
                                    // записать его id во вторую таблицу
                                    // если в базе такого юзера нет - бросаем исключение
                                    val roomUser =user.login?. let {
                                        db.userDao.findByLogin(it)
                                    }?:throw RuntimeException("No such user in cache")
                                    //
                                    val roomRepos  = repos.map {repos->
                                        RoomGithubRepository(id =repos.id?:"",  name = repos.name?:"",
                                            forksCount=repos.forks?:0, userId = roomUser.id)
                                    }
                                    //пишем в таблицу репозитории roomRepos юзера roomUser  с заданным логином
                                    db.repositoryDao.insert(roomRepos)
                                    return@fromCallable  repos  //возвращаем тот же  Single<List<GithubUserRepos>>
                                }
                            }
                    }?:Single.error<List<GithubUserRepos>>(RuntimeException("User has no repos url"))
                        .subscribeOn(Schedulers.io())
                    //а если сети нет, то
                }else{
                    //возвращаем список репозиториев конкретного юзера из базы в виде Single
                    Single.fromCallable {
                        //находим юзера по логину в таблице юзеров? если логина нет, бросаем исключение
                        val roomUser =user.login?. let {
                            db.userDao.findByLogin(it)
                        }?:throw RuntimeException("No such user in cache")
                        //находим список репозиториев конкретного юзера по его id  - в таблице это  userId
                        db.repositoryDao.findForUser(roomUser.id).map {room->
                            GithubUserRepos(id= room.id,name=room.name, forks = room.forksCount)
                            }
                    }
                }
            }.subscribeOn(Schedulers.io())
}