package com.example.popularlibs_homrworks.model.room.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

/*будем использовать отдельные классы пользователя и репозитория для работы с базой, чтобы не
 вносить изменений в существующие сущности во избежание создания зависимости логики от Room
 RoomGithubUser будет представлять таблицу пользователей:*/
@Entity
class RoomGithubUser (
    @PrimaryKey var id: String,
    var login: String,
    var avatarUrl: String,
    var repos_url:String
)