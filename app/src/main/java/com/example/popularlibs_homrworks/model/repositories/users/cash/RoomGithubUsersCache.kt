package com.example.popularlibs_homrworks.model.repositories.users.cash

import com.example.popularlibs_homrworks.model.entity.GithubUser
import com.example.popularlibs_homrworks.model.room.Database
import com.example.popularlibs_homrworks.model.room.tables.RoomGithubUser
import io.reactivex.rxjava3.core.Single

//класс для реализации кэширования списка пользователей из сети в базу данных
//метод doUsersCache - это метод интерфейса IRoomGithubUsersCache
class RoomGithubUsersCache:    IRoomGithubUsersCache {

    override fun doUsersCache(githubUserList: List<GithubUser>, db: Database
    ): Single<List<GithubUser>> {
       return Single.fromCallable{ //создаём  Single из списка, по пути пишем в базу
            // map для базы, так как классы разные
            val roomUsers = githubUserList.map {user->
                RoomGithubUser(
                    user.id ?: "", user.login ?: "",
                    user.avatarUrl ?: "", user.repos_url ?: ""
                )
            }
            db.userDao.insert(roomUsers) //пишем в базу
            return@fromCallable  githubUserList //возвращаем users  в виде Single<List<GithubUserRepos>>
        }
    }

}