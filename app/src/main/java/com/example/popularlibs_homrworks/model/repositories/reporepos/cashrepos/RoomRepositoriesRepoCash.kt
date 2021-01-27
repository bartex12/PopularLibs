package com.example.popularlibs_homrworks.model.repositories.reporepos.cashrepos

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubRepository
import io.reactivex.rxjava3.core.Single


//реализация интерфейса для кэширования списка репозиториев пользователя user из сети в базу данных
class RoomRepositoriesRepoCash :IRoomRepositiriesRepoCash{

    override fun doUserReposCache(
        user: GithubUser,
        repos: List<GithubUserRepos>,
        db: Database
    ): Single<List<GithubUserRepos>> {
       return Single.fromCallable {
            //получаем в базе юзера по его логину -для того, чтобы
            // записать его id во вторую таблицу
            val roomUser =user.login?. let {
                db.userDao.findByLogin(it)
                // если в базе такого юзера нет - бросаем исключение
            }?:throw RuntimeException("No such user in cache")
            //
            val roomRepos  = repos.map {repos->
                RoomGithubRepository(
                    id = repos.id ?: "", name = repos.name ?: "",
                    forksCount = repos.forks ?: 0, userId = roomUser.id
                )
            }
            //пишем в таблицу все репозитории roomRepos юзера roomUser  с заданным логином login
            db.repositoryDao.insert(roomRepos)
            return@fromCallable  repos
        }
    }
}