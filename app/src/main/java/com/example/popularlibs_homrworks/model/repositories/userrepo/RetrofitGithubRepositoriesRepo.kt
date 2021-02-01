package com.example.popularlibs_homrworks.model.repositories.userrepo

import com.example.popularlibs_homrworks.model.api.IDataSource
import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.network.INetworkStatus
import com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos.IRoomRepositiriesRepoCash
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RetrofitGithubRepositoriesRepo(val api: IDataSource,
            val networkStatus: INetworkStatus, val roomCash: IRoomRepositiriesRepoCash
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
                                roomCash.doUserReposCache(user, repos)
                            }
                    }?:Single.error<List<GithubUserRepos>>(RuntimeException("User has no repos url"))
                        .subscribeOn(Schedulers.io())
                    //а если сети нет, то
                }else{
            //если сети нет, получаем из базы данных список репозиториев, если его туда писали
                    roomCash.getUsersReposFromCash(user)
                }
            }.subscribeOn(Schedulers.io())
}