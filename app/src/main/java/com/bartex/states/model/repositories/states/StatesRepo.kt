package com.bartex.states.model.repositories.states

import android.util.Log
import com.bartex.states.model.api.state.IDataSourceState
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.network.INetworkStatus
import com.bartex.states.model.repositories.states.cash.IRoomStateCash
import com.bartex.states.model.room.Database
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

//Получаем интерфейс через конструктор и пользуемся им для получения пользователей.
// Обратите внимание, что поток, на который мы подписываемся, указан именно тут.
// Таким образом, мы позволяем репозиторию самостоятельно следить за тем, чтобы сетевые вызовы
// выполнялись именно в io-потоке. Всегда лучше поступать именно таким образом, даже когда речь
// не идёт о сети — во избежание выполнения операций в неверном потоке в вызывающем коде.
class StatesRepo(val api: IDataSourceState, val networkStatus: INetworkStatus,
val db:Database, val roomCash: IRoomStateCash):    IStatesRepo {
    //метод  интерфейса IDataSourceState getStates() - в зависимости от статуса сети
    //мы или получаем данные из сети, записывая их в базу данных с помощью Room через map
    //или берём из базы, преобразуя их также через map
    override fun getStates(): Single<List<State>> =
        networkStatus.isOnlineSingle()
            .flatMap {isOnLine-> //получаем доступ к Boolean значениям
                if (isOnLine){ //если сеть есть
                    Log.d(TAG, "StatesRepo  isOnLine  = true")
                    api.getStates() //получаем данные из сети в виде Single<List<State>>
                        .flatMap {states->  //получаем доступ к списку List<State>
                            //реализация кэширования списка пользователей из сети в базу данных
                            roomCash.doStatesCash(states, db)
                        }
                }else{
                    Log.d(TAG, "StatesRepo  isOnLine  = false")
                            //получение списка пользователей из кэша
                            roomCash.getStatesFromCash(db)
                }
            }.subscribeOn(Schedulers.io())
}