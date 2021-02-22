package com.bartex.states.presenter

import moxy.MvpView

interface IBaseView: MvpView {

    fun init()
    fun updateList()
}