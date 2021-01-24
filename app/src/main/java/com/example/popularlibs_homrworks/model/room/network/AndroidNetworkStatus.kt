package com.example.popularlibs_homrworks.model.room.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subjects.BehaviorSubject

class AndroidNetworkStatus(context: Context):INetworkStatus {
    private val statusSubject:BehaviorSubject<Boolean> = BehaviorSubject.create()

    init {
        statusSubject.onNext(false)
        //Тут мы используем ConnectivityManager и его возможность зарегистрировать слушатель
        // изменения статуса сети с помощью registerNetworkCallback
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //connectivityManager запрос делает,  но не сам а через NetworkRequest.Builder()
        //а ответ приходит в колбэке от слушателя
        val request = NetworkRequest.Builder().build()
        connectivityManager.registerNetworkCallback(request, object :
            ConnectivityManager.NetworkCallback(){
            //нужно переопределять вручную, так как это методы класса, а не интерфейса
            override fun onAvailable(network: Network) {
                statusSubject.onNext(true)
            }
            override fun onUnavailable() {
                statusSubject.onNext(false)
            }
            override fun onLost(network: Network) {
                statusSubject.onNext(false)
            }
        })
    }
   // возможность подписаться на изменения статуса сети
    override fun isOnline(): Observable<Boolean> = statusSubject
    //получить статус сети  один раз -- по умолчанию -false
    override fun isOnlineSingle(): Single<Boolean> = statusSubject.first(false)
}