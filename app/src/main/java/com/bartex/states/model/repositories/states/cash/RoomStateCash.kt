package com.bartex.states.model.repositories.states.cash

import com.bartex.states.model.entity.state.State
import com.bartex.states.model.room.Database
import com.bartex.states.model.room.tables.RoomState
import io.reactivex.rxjava3.core.Single

class RoomStateCash: IRoomStateCash {

    override fun doStatesCash(listStstes: List<State>, db: Database): Single<List<State>> {
     return  Single.fromCallable { //создаём  Single из списка, по пути пишем в базу
         // map для базы, так как классы разные
         val roomUsers = listStstes.map {state->
             RoomState(
                 state.capital ?: "", state.flag ?: "",
                 state.name ?: "", state.region ?: "",
                 state.population ?: 0, state.area?:0f
             )
         }
         db.stateDao.insert(roomUsers) //пишем в базу
           return@fromCallable listStstes //возвращаем states  в виде Single<List<State>>
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