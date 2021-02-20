package com.bartex.states.model.repositories.states.cash

import android.util.Log
import com.bartex.states.model.entity.state.State
import com.bartex.states.model.room.Database
import com.bartex.states.model.room.tables.RoomState
import io.reactivex.rxjava3.core.Single

class RoomStateCash(val db: Database): IRoomStateCash {

    companion object{
        const val TAG = "33333"
    }

    override fun doStatesCash(listStates: List<State>): Single<List<State>> {
     return  Single.fromCallable { //создаём  Single из списка, по пути пишем в базу
         // map для базы, так как классы разные
         val roomUsers = listStates.map {state->
             RoomState(
                 state.capital ?: "",
                 state.flag ?: "",
                 state.name ?: "",
                 state.region ?: "",
                 state.population ?: 0,
                 state.area?:0f,
                 state.latlng?.get(0) ?:0f,
                 state.latlng?.get(1) ?:0f
             )
         }
           db.stateDao.insert(roomUsers) //пишем в базу
           Log.d(TAG, "RoomStateCash doStatesCash: roomUsers.size = ${roomUsers.size}")
           return@fromCallable listStates //возвращаем states_search  в виде Single<List<State>>
       }
    }

    override fun getStatesFromCash(): Single<List<State>> {
      return  Single.fromCallable {
          db.stateDao.getAll().map {roomState->
              State(roomState.capital,roomState.flag, roomState.name, roomState.region,
                  roomState.population, roomState.area, arrayOf(roomState.lat, roomState.lng))
          }
        }
    }

    override fun getSearchedStatesFromCash(search: String) : Single<List<State>>{
        return  Single.fromCallable {
            db.stateDao.findByName(search).map {roomState->
                State(roomState.capital,roomState.flag, roomState.name, roomState.region,
                    roomState.population, roomState.area, arrayOf(roomState.lat, roomState.lng))
            }
        }
    }
}