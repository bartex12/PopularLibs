package com.example.popularlibs_homrworks.model.repositories.userrepo.cashrepos

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.entity.GithubUserRepos
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubRepository
import io.reactivex.rxjava3.core.Single


//реализация интерфейса для кэширования списка репозиториев пользователя user из сети в базу данных
class RoomRepositoriesRepoCash(val db: Database) :IRoomRepositiriesRepoCash{

    override fun doUserReposCache(
        user: GithubUser,
        repos: List<GithubUserRepos>
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

    override fun getUsersReposFromCash(user:GithubUser): Single<List<GithubUserRepos>> {
        //возвращаем список репозиториев конкретного юзера из базы в виде Single
        return Single.fromCallable {
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
}