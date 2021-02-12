package com.bartex.states.model.repositories.states.cash

import android.util.Log
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.room.Database
import com.bartex.states.model.room.tables.RoomState
import com.bartex.states.view.main.TAG
import io.reactivex.rxjava3.core.Single

class RoomStateCash: IRoomStateCash {

    override fun doStatesCash(listStates: List<State>, db: Database): Single<List<State>> {
     return  Single.fromCallable { //создаём  Single из списка, по пути пишем в базу
         // map для базы, так как классы разные
         val roomUsers = listStates.map {state->
//             Log.d(TAG, "RoomStateCash doStatesCash: lat = ${state.latlng?.get(0)}" +
//                     "  lng =  ${state.latlng?.get(1)}")
             RoomState(
                 state.capital ?: "", state.flag ?: "",
                 state.name ?: "", state.region ?: "",
                 state.population ?: 0, state.area?:0f,
                 state.latlng?.get(0) ?:0f, state.latlng?.get(1) ?:0f
             )
         }
         Log.d(TAG, "RoomStateCash doStatesCash: roomUsers.size = ${roomUsers.size}")
        db.stateDao.insert(roomUsers) //пишем в базу
           return@fromCallable listStates //возвращаем states  в виде Single<List<State>>
       }
    }

    override fun getStatesFromCash(db: Database): Single<List<State>> {
      return  Single.fromCallable {
          db.stateDao.getAll().map {roomState->
              State(roomState.capital,roomState.flag, roomState.name, roomState.region,
                  roomState.population, roomState.area)
          }
        }
    }
}