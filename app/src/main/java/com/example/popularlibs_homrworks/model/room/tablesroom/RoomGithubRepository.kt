package com.example.popularlibs_homrworks.model.room.tablesroom

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/*
*  Так как пользователь и репозиторий состоят в отношении «один ко многим», нам потребуется
*  внешний ключ. Для этого мы создали поле userId, которое связали с полем id в классе
* RoomGithubUser с помощью аргумента аннотации. В аргумент foreignKeys передаётся массив
*  всех внешних ключей. В нашем случае он один
* CASCADE, чтобы дочерние сущности исчезали при удалении родительской, так как репозитории
*  без пользователя нам не интересны. */
@Entity(
    foreignKeys = [
    ForeignKey(
     entity = RoomGithubUser::class,
        parentColumns = ["id"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RoomGithubRepository(
    @PrimaryKey var id:String,
    var name:String,
    var forksCount:Int,
    var userId: String
)