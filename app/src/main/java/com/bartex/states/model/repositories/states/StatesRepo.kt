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
                        .flatMap {states->//получаем доступ к списку List<State>
                            val filtr_states =   states.filter {state->
                                state.capital!=null &&  //только со столицами !=null
                                state.latlng?.size == 2 && //только с известными координатами
                                state.capital.isNotEmpty() //только с известными столицами
                            }
                            Log.d(TAG, "StatesRepo  getStates states.size = ${filtr_states.size}")
                            //реализация кэширования списка пользователей из сети в базу данных
                           roomCash.doStatesCash(filtr_states, db)
                        }
                }else{
                    Log.d(TAG, "StatesRepo  isOnLine  = false")
                            //получение списка пользователей из кэша
                            roomCash.getStatesFromCash(db)
                }
            }.subscribeOn(Schedulers.io())
}