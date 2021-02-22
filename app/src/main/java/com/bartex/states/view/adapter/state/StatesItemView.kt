package com.bartex.states.view.adapter.state

import com.bartex.states.view.adapter.IItemView

interface StatesItemView: IItemView {
    fun setName(name: String)
    fun loadFlag(url: String)
}