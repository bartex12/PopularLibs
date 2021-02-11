package com.bartex.states.view.adapter

interface StatesItemView:
    IItemView {
    fun setName(name: String)
    fun loadFlag(url: String)
}