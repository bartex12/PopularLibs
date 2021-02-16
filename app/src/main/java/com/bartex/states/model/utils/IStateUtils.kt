package com.bartex.states.model.utils

import com.bartex.states.model.entity.state.State

interface IStateUtils {
    fun getStateArea(state: State?):String
    fun getStatePopulation(state: State?):String
    fun getStatezoom(state: State?):String
    fun getStateCapital(state: State?):String
}