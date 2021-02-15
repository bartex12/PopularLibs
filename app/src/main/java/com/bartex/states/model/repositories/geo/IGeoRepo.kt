package com.bartex.states.model.repositories.geo

import com.bartex.states.model.entity.state.State

interface IGeoRepo {
   fun  sendGeoIntent(state: State?)
}