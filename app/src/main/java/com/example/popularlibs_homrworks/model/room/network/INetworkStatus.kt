package com.example.popularlibs_homrworks.model.room.network

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

/*
* Предусмотрим возможность подписаться на изменения статуса сети с помощью isOnline()
* или получить его один раз с помощью isOnlineSingle().
* реализация в классе AndroidNetworkStatus(context: Context) : INetworkStatus
*/
interface INetworkStatus {
    fun isOnline(): Observable<Boolean>
    fun isOnlineSingle(): Single<Boolean>
}