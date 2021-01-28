package com.example.popularlibs_homrworks.model.repositories.reporepos

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.network.INetworkStatus
import com.example.popularlibs_homrworks.model.repositories.reporepos.cashrepos.IRoomRepositiriesRepoCash
import com.example.popularlibs_homrworks.model.room.Database
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(val api: IDataSource, val networkStatus: INetworkStatus,
                                     val db: Database, val roomCash: IRoomRepositiriesRepoCash
):IGithubRepositoriesRepo {

    override fun getUserRepo(user: GithubUser): Single<List<GithubUserRepos>> =
        networkStatus.isOnlineSingle().flatMap {isOnLine->
            //если сеть есть, то
                if (isOnLine){
                    //если repos_url есть,получаем репозитории, пишем их в базу
                    //а если нет -  делаем вызов Single.error
                    user.repos_url?. let{url->
                        api.getUserRepos(url) //получаем репозитории в виде  Single<List<GithubUserRepos>>
                            .flatMap {repos->  //доступ к List<GithubUserRepos>
                                //реализация кэширования списка репозиториев
                                // конкретного пользователя user из сети в базу данных
                                roomCash.doUserReposCache(user, repos, db)
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
                      val list  =   db.repositoryDao.findForUser(roomUser.id).map {room->
                            GithubUserRepos(id= room.id,name=room.name, forks = room.forksCount)
                            }
                        return@fromCallable list
                    }
                }
            }.subscribeOn(Schedulers.io())
}